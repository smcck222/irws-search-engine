package cs7is3;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.core.FlattenGraphFilter;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.synonym.SynonymGraphFilter;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.synonym.WordnetSynonymParser;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.analysis.TokenStream;

public class CustomAnalyzer extends Analyzer
{
    // Stopword paths
    private static final String SW_TINY_PATH = "./data/stopwords/en_tiny.txt";
    private static final String SW_SHORT_PATH = "./data/stopwords/en_short.txt";
    private static final String SW_LONG_PATH = "./data/stopwords/en_long.txt";
    // Synonym paths
    private static final String SYN_GEO_PATH = "./data/synonyms/geo.txt";
    private static final String SYN_WORDNET_PATH = "./data/synonyms/wn_s.pl";

    private CharArraySet stopwords;
    private SynonymMap geoSynonyms = null;
    private SynonymMap wordnetSynonyms = null;

    public CustomAnalyzer(
        SearchEngine.ArgStopwords argStopwords,
        SearchEngine.ArgSynonyms argSynonyms
    ) throws IOException, ParseException
    {
        this.stopwords = getStopwords(argStopwords);
        
        if (argSynonyms == SearchEngine.ArgSynonyms.BOTH || argSynonyms == SearchEngine.ArgSynonyms.GEO)
        {
            this.geoSynonyms = getGeoSynonyms();
        }
        if (argSynonyms == SearchEngine.ArgSynonyms.BOTH || argSynonyms == SearchEngine.ArgSynonyms.WORDNET)
        {
            this.wordnetSynonyms = getWordnetSynonyms();
        }
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

    private SynonymMap getGeoSynonyms() throws IOException, ParseException
    {
        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        List<String> entries = Files.readAllLines(Paths.get(SYN_GEO_PATH));
        
        for (String entry : entries)
        {
            String[] split = entry.split(":");
            String[] keys = split[0].split(",");
            String[] values = split[1].split(",");

            for (String key : keys)
            {
                for (String value : values)
                {
                    builder.add(new CharsRef(key), new CharsRef(value), true);
                }
            }
        }

        return builder.build();
    }

    private SynonymMap getWordnetSynonyms() throws IOException, ParseException
    {
        WordnetSynonymParser parser = new WordnetSynonymParser(true, false, new WhitespaceAnalyzer());
        parser.parse(new FileReader(SYN_WORDNET_PATH));
        return parser.build();
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
        if (this.geoSynonyms != null)
        {
            stream = new FlattenGraphFilter(new SynonymGraphFilter(stream, this.geoSynonyms, true));
        }
        if (this.wordnetSynonyms != null)
        {
            stream = new FlattenGraphFilter(new SynonymGraphFilter(stream, this.wordnetSynonyms, true));
        }
        stream = new PorterStemFilter(stream);

        return new TokenStreamComponents(tokenizer, stream);
    }
}
