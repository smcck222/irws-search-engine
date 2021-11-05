package cs7is3;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.xml.sax.SAXException;

public class SearchEngine
{
    private Analyzer analyzer;
    private Indexer indexer;

    private SearchEngine() throws IOException, ParserConfigurationException, SAXException
    {
        analyzer = new CustomAnalyzer();
        indexer = new Indexer(analyzer);
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length == 0 || !args[0].matches("^(0|1)$"))
        {
            printHelpText();
            System.exit(1);
        }
        
        SearchEngine engine = new SearchEngine();

        if (args[0].equals("0"))
        {
            engine.indexer.build();
        }
        else
        {
            // to-do
        }
    }

    private static void printHelpText()
    {
        System.out.println("Usage: java -jar target/searchengine-1.0.jar <MODE>");
        System.out.println("\t<MODE>\t(0=index, 1=score)");
    }
}
