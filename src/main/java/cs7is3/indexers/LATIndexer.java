package cs7is3.indexers;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StringField;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;

public class LATIndexer
{
      public static Document fillDocument(Element element, Document document)
    {
        // Add the <DOCNO> and avoid the Null value
        if (element.getElementsByTag("DOCNO").first() == null)
            document.add(new StringField("DOCNO", " ", Field.Store.YES));
        else {
            String docNumber = element.getElementsByTag("DOCNO").first().text();
            document.add(new StringField("DOCNO", docNumber, Field.Store.YES));
        }

        // Add the <HEADLINE> and avoid the Null value
        if (element.getElementsByTag("HEADLINE").first() == null)
            document.add(new TextField("HEADLINE", " ", Field.Store.YES));
        else {
            String headline = element.getElementsByTag("HEADLINE").first().text();
            document.add(new TextField("HEADLINE", headline, Field.Store.YES));
        }

        // Add the <TEXT> and avoid the Null value
        if(element.getElementsByTag("TEXT").first() == null)
            document.add(new TextField("TEXT"," ",Field.Store.YES));
        else {
            String text = element.getElementsByTag("TEXT").first().text();
            document.add(new TextField("TEXT", text, Field.Store.YES));
        }

        List<String> extraFields = Arrays.asList("DOCID","DATE","LENGTH","GRAPHIC","SECTION","SUBJECT","TYPE");
        for (int i = 0; i < extraFields.size(); i++) {
            addExtraField(extraFields.get(i), element, document);
        }
        
        return document;
    }

    public static void addExtraField(String field, Element element, Document document) {
        // Add the text inside an element of type field, if it exists.
        Element node = element.getElementsByTag(field).first();
        if (node != null) {
            document.add(new TextField(field, node.text().replaceAll("\n+", "\n"), Field.Store.YES));
        }
    }
}
