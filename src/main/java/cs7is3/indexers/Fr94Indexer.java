package cs7is3.indexers;

import java.util.Arrays;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.jsoup.nodes.Element;

public class Fr94Indexer
{
    public static Document fillDocument(Element element, Document document)
    {
        // Add the DOCNO as an ID.
        String docNumber = element.getElementsByTag("DOCNO").first().text();
        document.add(new TextField("DOCNO", docNumber, Field.Store.YES));

        // Add all of the text inside <TEXT>, including text inside nested elements.
        String allText = element.getElementsByTag("TEXT").first().text().replaceAll("\n+", "\n");
        document.add(new TextField("TEXT", allText, Field.Store.YES));

        // Add the text inside <TEXT> without any of the text inside nested elements.
        String outerText = element.getElementsByTag("TEXT").first().ownText().replaceAll("\n+", "\n");
        document.add(new TextField("OUTER_TEXT", outerText, Field.Store.YES));

        List<String> extraFields = Arrays.asList("AGENCY", "ACTION", "SUMMARY", "FURTHER", "SUPPLEM");
        for (int i = 0; i < extraFields.size(); i++) {
            addExtraField(extraFields.get(i), element, document);
        }
        return document;
    }

    public static Document addExtraField(String field, Element element, Document document)
    {
        // Add the text inside an element of type field, if it exists.
        Element node = element.getElementsByTag(field).first();
        if (node != null) {
            document.add(new TextField(field, node.text().replaceAll("\n+", "\n"), Field.Store.YES));
        }
        return document;
    }
}
