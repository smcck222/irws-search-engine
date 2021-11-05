package cs7is3;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;

public class SearchEngine
{
    private Analyzer analyzer;
    private Indexer indexer;

    private SearchEngine() throws ParserConfigurationException
    {
        analyzer = new CustomAnalyzer();
        indexer = new Indexer(analyzer);
    }

    public static void main(String[] args) throws Exception
    {
        SearchEngine engine = new SearchEngine();
        engine.indexer.buildIndex();
    }
}
