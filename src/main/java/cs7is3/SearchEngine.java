package cs7is3;

import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import picocli.CommandLine;
import picocli.CommandLine.Option;

public class SearchEngine implements Runnable
{
    // Default directories and paths
    public static final String CORPUS_DIRECTORY = "./data/corpus";
    public static final String INDEX_DIRECTORY = "./data/index";
    public static final String TOPICS_PATH = "./data/topics";
    public static final String RESULTS_PATH_FMT = "./data/results/%s";

    // Enums for command line arguments
    public enum ArgMode { INDEX, SEARCH }
    public enum ArgAnalyzer { STANDARD, ENGLISH, CUSTOM }
    public enum ArgScorer { BM25, CLASSIC, LMJM, CUSTOM }
    public enum ArgParser { DEFAULT, MULTI }
    public enum ArgStopwords { TINY, SHORT, LONG }
    public enum ArgSynonyms { NONE, GEO, WORDNET, BOTH }

    @Option(names={"-m", "--mode"}, required=true, description="Mode of operation (INDEX, SEARCH)")
    private ArgMode argMode;

    @Option(names={"-a", "--analyzer"}, required=false, description="Analyzer to use (STANDARD, ENGLISH, CUSTOM)")
    private ArgAnalyzer argAnalyzer = ArgAnalyzer.CUSTOM;

    @Option(names={"-s", "--scorer"}, required=false, description="Scoring algorithm to use (BM25, CLASSIC, LMJM, CUSTOM)")
    private ArgScorer argScorer = ArgScorer.CUSTOM;

    @Option(names={"-p", "--parser"}, required=false, description="Query parser to use (DEFAULT, MULTI)")
    private ArgParser argParser = ArgParser.MULTI;

    @Option(names={"--stopwords"}, required=false, description="Stopword list to use (TINY, SHORT, LONG)")
    private ArgStopwords argStopwords = ArgStopwords.LONG;

    @Option(names={"--synonyms"}, required=false, description="Synonym list to use (NONE, GEO, WORDNET, BOTH)")
    private ArgSynonyms argSynonyms = ArgSynonyms.NONE;

    @Option(names={"-h", "--help"}, usageHelp=true, description="Display this help message")
    private boolean argHelp = false;

    @Override
    public void run()
    {
        try
        {
            Analyzer analyzer = getAnalyzer();

            if (argMode == ArgMode.INDEX)
            {
                Indexer indexer = new Indexer(analyzer);
                indexer.build();
            }
            else
            {
                String runName = getRunName();
                Searcher searcher = new Searcher(analyzer, argScorer, argParser, runName);
                searcher.score();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Analyzer getAnalyzer() throws IOException, ParseException
    {
        Analyzer analyzer = null;
        switch (argAnalyzer)
        {
            case STANDARD:
                analyzer = new StandardAnalyzer();
                break;
            case ENGLISH:
                analyzer = new EnglishAnalyzer();
                break;
            case CUSTOM:
                analyzer = new CustomAnalyzer(argStopwords, argSynonyms);
                break;
            default:
                // will never be reached
                break;
        }

        return analyzer;
    }

    private String getRunName()
    {
        String strAnalyzer = StringUtils.capitalize(argAnalyzer.name().toLowerCase());
        String strScorer = StringUtils.capitalize(argScorer.name().toLowerCase());
        String strParser = StringUtils.capitalize(argParser.name().toLowerCase());
        String strStopwords = "";
        String strSynonyms = "";
        
        if (argAnalyzer == ArgAnalyzer.CUSTOM)
        {
            strStopwords = StringUtils.capitalize(argStopwords.name().toLowerCase());
            strSynonyms = StringUtils.capitalize(argSynonyms.name().toLowerCase());
        }

        return strAnalyzer + strScorer + strParser + strStopwords + strSynonyms;
    }

    public static void main(String[] args)
    {
        new CommandLine(new SearchEngine()).execute(args);
    }
}
