package cs7is3;

import org.apache.lucene.analysis.Analyzer;

public class SearchEngine
{
    private Analyzer analyzer;
    private Indexer indexer;
    private Searcher searcher;

    private SearchEngine()
    {
        analyzer = new CustomAnalyzer();
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
            engine.indexer = new Indexer(engine.analyzer);
            engine.indexer.build();
        }
        else
        {
            engine.searcher = new Searcher(engine.analyzer);
            engine.searcher.score();
        }
    }

    private static void printHelpText()
    {
        System.out.println("Usage: java -jar target/searchengine-1.0.jar <MODE>");
        System.out.println("\t<MODE>\t(0=index, 1=score)");
    }
}
