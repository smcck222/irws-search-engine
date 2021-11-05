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
        SearchEngine engine = new SearchEngine();
        engine.indexer.build();
        engine.indexer.close();
    }
}
