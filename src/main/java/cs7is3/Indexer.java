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

import org.xml.sax.SAXException;

import org.w3c.dom.Document;

public class Indexer
{
    private Analyzer analyzer;
    private Cleaner cleaner;
    private DocumentBuilder builder;

    public Indexer(Analyzer analyzer) throws ParserConfigurationException
    {
        this.analyzer = analyzer;
        this.cleaner = new Cleaner();
        this.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public void buildIndex() throws IOException, ParserConfigurationException
    {
        indexSourceDocuments(Source.FBIS);
        indexSourceDocuments(Source.FR);
        indexSourceDocuments(Source.FT);
        indexSourceDocuments(Source.LAT);
    }

    private void indexSourceDocuments(Source source) throws IOException, ParserConfigurationException
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
            try
            {
                Document xmlDocument = builder.parse(stream);
                // to-do: indexing begins here
            }
            catch (SAXException e)
            {
                System.out.println(filePath.toString());
                e.printStackTrace();
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
}
