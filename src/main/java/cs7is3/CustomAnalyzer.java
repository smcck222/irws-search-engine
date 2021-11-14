package cs7is3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;

public class CustomAnalyzer extends Analyzer
{
    private static final String STOPWORDS_PATH = "./data/stopwords/en_long.txt";

    private List<String> stopwords;

    public CustomAnalyzer() throws IOException
    {
        this.stopwords = loadStopwords();
    }

    private List<String> loadStopwords() throws IOException
    {
        return Files.readAllLines(Paths.get(STOPWORDS_PATH));
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName)
    {
        StandardTokenizer tokenizer = new StandardTokenizer();
        TokenStream stream;

        stream = new LowerCaseFilter(tokenizer);
        stream = new EnglishPossessiveFilter(stream);
        stream = new StopFilter(stream, new CharArraySet(this.stopwords, true));
        stream = new PorterStemFilter(stream);
        
        return new TokenStreamComponents(tokenizer, stream);
    }
}
