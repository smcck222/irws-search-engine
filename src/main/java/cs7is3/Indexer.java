package cs7is3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Indexer
{
    private Analyzer analyzer;
    private Cleaner cleaner;
    private DocumentBuilder builder;
    private Directory directory;
    private IndexWriter writer;

    public Indexer(Analyzer analyzer) throws IOException, ParserConfigurationException
    {
        this.analyzer = analyzer;
        this.cleaner = new Cleaner();
        this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public void build() throws IOException, ParserConfigurationException, SAXException
    {
        // Set up the configuration for the index writer
        IndexWriterConfig config = new IndexWriterConfig(this.analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        
        // Open the index writer and directory
        this.directory = FSDirectory.open(Paths.get(Constants.INDEX_DIRECTORY));
        this.writer = new IndexWriter(this.directory, config);

        // Index the documents for each of the data sources
        indexSourceDocuments(Source.FBIS);
        indexSourceDocuments(Source.FR);
        indexSourceDocuments(Source.FT);
        indexSourceDocuments(Source.LAT);

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
            // Load and clean the given file then return it as a stream of bytes
            ByteArrayInputStream stream = loadAndCleanFileByteStream(filePath, source);

            // Parse the bytes into an XML object
            org.w3c.dom.Document xml = builder.parse(stream);

            // Get a list of <doc> nodes from the XML
            NodeList nodes = xml.getElementsByTagName("DOC");
            
            // Convert each <doc> node into a Lucene document and add it to the index
            for (int i = 0; i < nodes.getLength(); i++)
            {
                Document document = getDocumentFromXml(nodes.item(i), source);
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

    private ByteArrayInputStream loadAndCleanFileByteStream(Path filePath, Source source) throws IOException
    {
        // Load the file as a string
        String fileString = Files.readString(filePath, StandardCharsets.ISO_8859_1);

        // Clean the string
        fileString = cleaner.clean(fileString, source);

        // Convert the string to a stream of bytes of and return
        return new ByteArrayInputStream(fileString.getBytes());
    }

    private Document getDocumentFromXml(Node xml, Source source)
    {
        Document document = new Document();

        // to-do

        return document;
    }
}
