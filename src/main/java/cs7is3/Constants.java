package cs7is3;

public class Constants
{
    // Directories and paths
    public static final String CORPUS_DIRECTORY = "./data/corpus";
    public static final String INDEX_DIRECTORY = "./data/index";
    public static final String TOPICS_PATH = "./data/topics";
    public static final String RESULTS_PATH = "./data/results";

    // Element tags (documents)
    public static final String DOCUMENT_TAG = "DOC";
    public static final String DOCUMENT_ID_TAG = "DOCNO";
    
    // Element tags (topics)
    public static final String TOPIC_TAG = "top";
    public static final String TOPIC_NUMBER_TAG = "num";
    public static final String TOPIC_TITLE_TAG = "title";
    public static final String TOPIC_DESCRIPTION_TAG = "desc";
    public static final String TOPIC_NARRATIVE_TAG = "narr";

    // Index fields
    public static final String FIELD_DOCUMENT_ID = "docno";
    public static final String FIELD_CONTENT = "content";

    // Searching
    public static final int MAX_SEARCH_RESULTS = 1000;
}
