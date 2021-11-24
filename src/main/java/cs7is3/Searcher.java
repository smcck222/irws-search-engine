package cs7is3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class Searcher
{
    private static final int MAX_SEARCH_RESULTS = 1000;

    private Analyzer analyzer;
    private Similarity similarity;
    private QueryParser parser;
    private String runName;
    private Directory directory;
    private DirectoryReader reader;
    private IndexSearcher searcher;
    private FileWriter file;
    private BufferedWriter buffer;

    public Searcher(
        Analyzer analyzer,
        SearchEngine.ArgScorer argScorer,
        SearchEngine.ArgParser argParser,
        String runName
    )
    {
        this.analyzer = analyzer;
        this.similarity = getSimilarity(argScorer);
        this.parser = getParser(argParser);
        this.runName = runName;
    }

    public void score() throws IOException, ParseException
    {
        // Open the directory and directory reader
        this.directory = FSDirectory.open(Paths.get(SearchEngine.INDEX_DIRECTORY));
        this.reader = DirectoryReader.open(this.directory);

        // Create an index searcher with the specified scoring algorithm
        this.searcher = new IndexSearcher(this.reader);
        this.searcher.setSimilarity(this.similarity);
        
        // Open a file and buffered writer
        String resultsPath = String.format(SearchEngine.RESULTS_PATH_FMT, this.runName);
        this.file = new FileWriter(resultsPath, false);
        this.buffer = new BufferedWriter(this.file);

        // Get a list of the search topics
        List<Topic> topics = getTopics();

        // Write the search results for each topic to file
        for (Topic topic : topics)
        {
            List<Result> results = getResults(topic);
            for (Result result : results)
            {
                this.buffer.write(result.toString());
                this.buffer.newLine();
            }
        }

        // Close everything
        this.buffer.close();
        this.file.close();
        this.reader.close();
        this.directory.close();
    }

    private Similarity getSimilarity(SearchEngine.ArgScorer argScorer)
    {
        Similarity similarity = null;
        switch (argScorer)
        {
            case BM25:
                similarity = new BM25Similarity();
                break;
            case CLASSIC:
                similarity = new ClassicSimilarity();
                break;
            case BOOLEAN:
                similarity = new BooleanSimilarity();
                break;
            case CUSTOM:
                similarity = new BM25Similarity(0.5f, 0.75f);
                break;
            default:
                // will never be reached
                break;
        }
        return similarity;
    }

    private QueryParser getParser(SearchEngine.ArgParser argParser)
    {
        if (argParser == SearchEngine.ArgParser.DEFAULT)
        {
            return new QueryParser(Indexer.FIELD_CONTENT, this.analyzer);
        }
        else
        {
            return new MultiFieldQueryParser(
                new String[] { Indexer.FIELD_TITLE, Indexer.FIELD_CONTENT },
                this.analyzer,
                new HashMap<String, Float>() {{
                    put(Indexer.FIELD_TITLE, 0.04f);
                    put(Indexer.FIELD_CONTENT, 1.0f);
                }}
            );
        }
    }

    private List<Topic> getTopics() throws IOException
    {
        // Read the topics file as a string
        byte[] fileBytes = Files.readAllBytes(Paths.get(SearchEngine.TOPICS_PATH));
        String fileString = new String(fileBytes, StandardCharsets.ISO_8859_1);
        
        // Parse the file contents into a JSoup object
        org.jsoup.nodes.Document soup = Jsoup.parse(fileString);

        // Get a list of topic elements from the object
        List<Element> elements = soup.getElementsByTag(Topic.TOPIC_TAG);
        
        // Convert each element into a Topic object
        List<Topic> topics = elements.stream().map(Topic::new).collect(Collectors.toList());

        return topics;
    }

    private List<Result> getResults(Topic topic) throws IOException, ParseException
    {
        // Generate the query and get the search hits
        Query query = generateQueryFromTopic(topic);
        ScoreDoc[] hits = this.searcher.search(query, MAX_SEARCH_RESULTS).scoreDocs;

        List<Result> results = new ArrayList<Result>();
        
        // Convert each search hit into a Result object
        for (int i = 0; i < hits.length; i++)
        {
            // Get the document ID
            Document document = this.searcher.doc(hits[i].doc);
            String documentId = document.get(Indexer.FIELD_DOCUMENT_ID);

            Result result = new Result(topic.number, documentId, i + 1, hits[i].score, this.runName);
            results.add(result);
        }

        return results;
    }

    private Query generateQueryFromTopic(Topic topic) throws ParseException
    {
        // Extract relevant sections of narrative
        String relevantNarrative = extractRelevantNarrative(topic);

        // Create separate queries of different weights for each topic section
        Query queryTitle = new BoostQuery(this.parser.parse(topic.title), 1.0f);
        Query queryDescription = new BoostQuery(this.parser.parse(topic.description), 0.35f);
        Query queryNarrativeRelevant = new BoostQuery(this.parser.parse(relevantNarrative), 0.3f);
        
        // Combine the queries together
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder.add(queryTitle, BooleanClause.Occur.SHOULD);
        builder.add(queryDescription, BooleanClause.Occur.SHOULD);
        builder.add(queryNarrativeRelevant, BooleanClause.Occur.SHOULD);
        
        return builder.build();
    }

    private String extractRelevantNarrative(Topic topic)
    {
        String result = "";

        String[] clauses = topic.narrative.split("\\.");
        for (String clause : clauses)
        {
            clause = clause.replace("\n", " ").replace(" +", " ").trim();
            if (!clause.matches(".*(irrelevant|not relevant).*"))
            {
                result += " " + clause;
            }
        }

        return result.isEmpty() ? topic.narrative : result;
    }
}
