package cs7is3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

public class Searcher
{
    private Analyzer analyzer;

    public Searcher(Analyzer analyzer)
    {
        this.analyzer = analyzer;
    }

    public void score() throws IOException
    {
        List<Topic> topics = getTopics();
    }

    private List<Topic> getTopics() throws IOException
    {
        // Read the topics file as a string
        byte[] fileBytes = Files.readAllBytes(Paths.get(Constants.TOPICS_PATH));
        String fileString = new String(fileBytes, StandardCharsets.ISO_8859_1);
        
        // Parse the file contents into a JSoup object
        org.jsoup.nodes.Document soup = Jsoup.parse(fileString);

        // Get a list of topic elements from the object
        List<Element> elements = soup.getElementsByTag(Constants.TOPIC_TAG);
        
        // Convert each element into a Topic object
        List<Topic> topics = elements.stream().map(Topic::new).collect(Collectors.toList());

        return topics;
    }
}
