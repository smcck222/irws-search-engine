package cs7is3;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.synonym.WordnetSynonymParser;
import org.apache.lucene.analysis.TokenStream;

public class CustomAnalyzer extends Analyzer
{
    // Stopword paths
    private static final String SW_TINY_PATH = "./data/stopwords/en_tiny.txt";
    private static final String SW_SHORT_PATH = "./data/stopwords/en_short.txt";
    private static final String SW_LONG_PATH = "./data/stopwords/en_long.txt";
    // Synonym paths
    private static final String SYN_GEO_PATH = "./data/synonyms/geo.pl";
    private static final String SYN_WORDNET_PATH = "./data/synonyms/wn_s.pl";

    private CharArraySet stopwords;
    private SynonymMap synonyms;

    public CustomAnalyzer(
        SearchEngine.ArgStopwords argStopwords,
        SearchEngine.ArgSynonyms argSynonyms
    ) throws IOException, ParseException
    {
        this.stopwords = getStopwords(argStopwords);
        this.synonyms = getSynonyms(argSynonyms);
    }

    private CharArraySet getStopwords(SearchEngine.ArgStopwords argStopwords) throws IOException
    {
        String path = "";
        switch (argStopwords)
        {
            case TINY:
                path = SW_TINY_PATH;
                break;
            case SHORT:
                path = SW_SHORT_PATH;
                break;
            case LONG:
                path = SW_LONG_PATH;
                break;
            default:
                // will never be reached
                break;
        }
        
        CharArraySet stopwords = new CharArraySet(Files.readAllLines(Paths.get(path)), true);
        return stopwords;
    }

    private SynonymMap getSynonyms(SearchEngine.ArgSynonyms argSynonyms) throws IOException, ParseException
    {
        if (argSynonyms == SearchEngine.ArgSynonyms.NONE)
        {
            return null;
        }

        String path = "";
        switch (argSynonyms)
        {
            case GEO:
                path = SYN_GEO_PATH;
                break;
            case WORDNET:
                path = SYN_WORDNET_PATH;
                break;
            default:
                // will never be reached
                break;
        }

        WordnetSynonymParser synonymParser = new WordnetSynonymParser(true, false, new WhitespaceAnalyzer());
        synonymParser.parse(new FileReader(path));
        SynonymMap synonyms = synonymParser.build();
        return synonyms;
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName)
    {
        StandardTokenizer tokenizer = new StandardTokenizer();
        TokenStream stream;

        stream = new LowerCaseFilter(tokenizer);
        stream = new TrimFilter(stream);
        stream = new EnglishPossessiveFilter(stream);
		stream = new StopFilter(stream, this.stopwords);
        if (this.synonyms != null)
        {
            // add synonyms filter
        }
        stream = new PorterStemFilter(stream);

        return new TokenStreamComponents(tokenizer, stream);
    }
}
