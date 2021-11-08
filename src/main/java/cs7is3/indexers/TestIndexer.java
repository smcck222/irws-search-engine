package cs7is3.indexers;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.jsoup.nodes.Element;

import cs7is3.Constants;

public class TestIndexer
{
    public static Document fillDocument(Element element, Document document)
    {
        String documentId = element.getElementsByTag(Constants.DOCUMENT_ID_TAG).first().text();
        document.add(new TextField(Constants.FIELD_DOCUMENT_ID, documentId, Field.Store.YES));
        
        String documentContent = element.text();
        document.add(new TextField(Constants.FIELD_CONTENT, documentContent, Field.Store.YES));
        
        return document;
    }
}
