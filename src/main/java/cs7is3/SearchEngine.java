package cs7is3;

import org.apache.lucene.analysis.Analyzer;

public class SearchEngine
{
    private Analyzer analyzer;
    private Indexer indexer;

    private SearchEngine()
    {
        analyzer = new CustomAnalyzer();
        indexer = new Indexer(analyzer);
    }

    public static void main(String[] args)
    {
        SearchEngine engine = new SearchEngine();
        try
        {
            engine.indexer.buildIndex();
        }
        catch (Exception e)
        {
            System.out.println("Indexing error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
