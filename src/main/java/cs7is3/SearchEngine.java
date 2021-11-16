package cs7is3;

import org.apache.lucene.analysis.Analyzer;

import picocli.CommandLine;
import picocli.CommandLine.Option;

public class SearchEngine implements Runnable
{
    // Default directories and paths
    public static final String CORPUS_DIRECTORY = "./data/corpus";
    public static final String INDEX_DIRECTORY = "./data/index";
    public static final String TOPICS_PATH = "./data/topics";
    public static final String RESULTS_PATH = "./data/results";

    enum ModeArg {INDEX, SEARCH}

    @Option(names={"-m", "--mode"}, required=true, description="Mode of operation (INDEX, SEARCH)")
    private ModeArg mode;

    @Option(names={"-r", "--results"}, required=false, description="The maximum number of search results to output")
    private int maxSearchResults = 1000;

    @Option(names={"-h", "--help"}, usageHelp=true, description="Display this help message")
    private boolean helpRequested = false;

    @Override
    public void run()
    {
        try
        {
            Analyzer analyzer = new CustomAnalyzer();

            if (mode == ModeArg.INDEX)
            {
                Indexer indexer = new Indexer(analyzer);
                indexer.build();
            }
            else
            {
                Searcher searcher = new Searcher(analyzer, maxSearchResults);
                searcher.score();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args)
    {
        new CommandLine(new SearchEngine()).execute(args);
    }
}
