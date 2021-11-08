package cs7is3;

import org.jsoup.nodes.Element;

public class Topic
{
    public int number;
    public String title;
    public String description;
    public String narrative;

    public Topic(Element element)
    {
        number = Integer.parseInt(getContentOfElementTag(element, Constants.TOPIC_NUMBER_TAG, true));
        title = getContentOfElementTag(element, Constants.TOPIC_TITLE_TAG, false);
        description = getContentOfElementTag(element, Constants.TOPIC_DESCRIPTION_TAG, true);
        narrative = getContentOfElementTag(element, Constants.TOPIC_NARRATIVE_TAG, true);
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

        return content;
    }
}
