package cs7is3.indexers;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StringField;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;

import cs7is3.Indexer;

public class LATIndexer
{
      public static Document fillDocument(Element element, Document document)
    {
        // Add the <DOCNO>
        String docNumber = element.getElementsByTag(Indexer.DOCUMENT_ID_TAG).first().text();
        document.add(new StringField(Indexer.FIELD_DOCUMENT_ID, docNumber, Field.Store.YES));

        String documentContent = element.text();
        document.add(new TextField(Indexer.FIELD_CONTENT, documentContent, Field.Store.YES));

        // Add the <HEADLINE>
        String headline = element.getElementsByTag("HEADLINE").text();
        document.add(new TextField(Indexer.FIELD_TITLE, headline, Field.Store.YES));

        // Add the <TEXT> and avoid the Null value
        if(element.getElementsByTag("TEXT").first() == null)
            document.add(new TextField("TEXT","",Field.Store.YES));
        else {
            String text = element.getElementsByTag("TEXT").first().text();
            document.add(new TextField("TEXT", text, Field.Store.YES));
        }

        List<String> extraFields = Arrays.asList("DOCID","DATE","LENGTH","GRAPHIC","SECTION",Indexer.FIELD_SUBJECT,"TYPE");
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
