package cs7is3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.xml.sax.SAXException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import cs7is3.indexers.*;

public class Indexer
{
    private Analyzer analyzer;
    private Directory directory;
    private IndexWriter writer;

    public Indexer(Analyzer analyzer) throws IOException, ParserConfigurationException
    {
        this.analyzer = analyzer;
    }

    public void build() throws IOException, ParserConfigurationException, SAXException
    {
        // Set up the configuration for the index writer
        IndexWriterConfig config = new IndexWriterConfig(this.analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        
        // Open the index writer and directory
        this.directory = FSDirectory.open(Paths.get(Constants.INDEX_DIRECTORY));
        this.writer = new IndexWriter(this.directory, config);

        // Logging
        long startTime = System.currentTimeMillis();
        
        // Index the documents for each of the data sources
        indexSourceDocuments(Source.FBIS);
        indexSourceDocuments(Source.FR);
        indexSourceDocuments(Source.FT);
        indexSourceDocuments(Source.LAT);

        // Logging
        System.out.println(String.format(
            "Indexing completed (%d seconds elapsed)",
            (System.currentTimeMillis() - startTime) / 1000
        ));

        // Close the index writer and directory
        this.writer.close();
        this.directory.close();
    }

    private void indexSourceDocuments(Source source) throws IOException, ParserConfigurationException, SAXException
    {
        // Get a list of paths to all the files for this data source
        List<Path> filePaths = getSourceFilePaths(source);

        // Logging
        System.out.println(String.format(
            "Indexing '%s' (%d files found)",
            source.toString().toUpperCase(), filePaths.size()
        ));

        // Parse each file separately
        for (Path filePath : filePaths)
        {
            // Read the file as a string
            byte[] fileBytes = Files.readAllBytes(filePath);
            String fileString = new String(fileBytes, StandardCharsets.ISO_8859_1);

            // Parse the file contents into a JSoup object
            org.jsoup.nodes.Document soup = Jsoup.parse(fileString);
            
            // Get a list of <doc> elements from the object
            List<Element> elements = soup.getElementsByTag("DOC");
            
            // Convert each <doc> node into a Lucene document and add it to the index
            for (Element element : elements)
            {
                Document document = getDocumentFromSoupElement(element, source);
                this.writer.addDocument(document);
            }
        }
    }

    private List<Path> getSourceFilePaths(Source source) throws IOException
    {
        Path directoryPath = Paths.get(Constants.CORPUS_DIRECTORY, source.toString());
        String fileNamePrefix = source.getFileNamePrefix();
        
        return Files.find(
            directoryPath,
            Integer.MAX_VALUE,
            // ignore directories and other files (readme, ds_store, etc.)
            (path, attr) -> attr.isRegularFile() && path.getFileName().toString().startsWith(fileNamePrefix)
        ).collect(Collectors.toList());
    }

    private Document getDocumentFromSoupElement(Element element, Source source)
    {
        Document document = new Document();
        switch (source)
        {
            case FBIS:
                document = FBISIndexer.fillDocument(element, document);
                break;
            case FR:
                document = Fr94Indexer.fillDocument(element, document);
                break;
            case FT:
                document = FTIndexer.fillDocument(element, document);
                break;
            case LAT:
                document = LATIndexer.fillDocument(element, document);
                break;
            default:
                // will never be reached
                break;
        }
        return document;
    }
}
