package cs7is3;

import org.jsoup.nodes.Element;

public class Topic
{
    // Topics file tags
    public static final String TOPIC_TAG = "top";
    public static final String TOPIC_NUMBER_TAG = "num";
    public static final String TOPIC_TITLE_TAG = "title";
    public static final String TOPIC_DESCRIPTION_TAG = "desc";
    public static final String TOPIC_NARRATIVE_TAG = "narr";

    public int number;
    public String title;
    public String description;
    public String narrative;

    public Topic(Element element)
    {
        number = Integer.parseInt(getContentOfElementTag(element, TOPIC_NUMBER_TAG, true));
        title = getContentOfElementTag(element, TOPIC_TITLE_TAG, false);
        description = getContentOfElementTag(element, TOPIC_DESCRIPTION_TAG, true);
        narrative = getContentOfElementTag(element, TOPIC_NARRATIVE_TAG, true);
    }

    private String getContentOfElementTag(Element element, String tag, Boolean stripHeading)
    {
        // Get the text of the desired element while ignoring the text of its children
        String content = element.getElementsByTag(tag).first().ownText();
        
        // Strip the redundant field heading, i.e. 'Number: '
        if (stripHeading)
        {
            content = content.substring(content.indexOf(':') + 2);
        }

        // Remove certain special characters
        content = content.replaceAll("/", " ");

        return content;
    }
}
