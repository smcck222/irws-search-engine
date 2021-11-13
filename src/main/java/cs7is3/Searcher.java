package cs7is3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class Searcher
{
    public static final int MAX_SEARCH_RESULTS = 1000;

    private Analyzer analyzer;
    private Directory directory;
    private DirectoryReader reader;
    private IndexSearcher searcher;
    private QueryParser parser;
    private FileWriter file;
    private BufferedWriter buffer;

    public Searcher(Analyzer analyzer)
    {
        this.analyzer = analyzer;
    }

    public void score() throws IOException, ParseException
    {
        // Open the directory and directory reader
        this.directory = FSDirectory.open(Paths.get(SearchEngine.INDEX_DIRECTORY));
        this.reader = DirectoryReader.open(this.directory);

        // Create an index searcher and query parser
        this.searcher = new IndexSearcher(this.reader);
        this.parser = new QueryParser(Indexer.FIELD_CONTENT, this.analyzer);

        // Open a file and buffered writer
        this.file = new FileWriter(SearchEngine.RESULTS_PATH, false);
        this.buffer = new BufferedWriter(this.file);

        // Get a list of the search topics
        List<Topic> topics = getTopics();

        // Write the search results for each topic to file
        for (Topic topic : topics)
        {
            List<Result> results = getResults(topic);
            for (Result result : results)
            {
                buffer.write(result.toString());
                buffer.newLine();
            }
        }

        // Close everything
        this.buffer.close();
        this.file.close();
        this.reader.close();
        this.directory.close();
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

            Result result = new Result(topic.number, documentId, i + 1, hits[i].score);
            results.add(result);
        }

        return results;
    }

    private Query generateQueryFromTopic(Topic topic) throws ParseException
    {
        // Create separate queries of different weights for each topic section
        Query queryTitle = new BoostQuery(this.parser.parse(topic.title), 1.0f);
        Query queryDescription = new BoostQuery(this.parser.parse(topic.description), 0.4f);
        Query queryNarrative = new BoostQuery(this.parser.parse(topic.narrative), 0.25f);
        
        // Combine the queries together (only the title *must* occur in the document)
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder.add(queryTitle, BooleanClause.Occur.MUST);
        builder.add(queryDescription, BooleanClause.Occur.SHOULD);
        builder.add(queryNarrative, BooleanClause.Occur.SHOULD);
        
        return builder.build();
    }
}
