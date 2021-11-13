package cs7is3.indexers;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.jsoup.nodes.Element;

import cs7is3.Indexer;
import cs7is3.Source;

public class TestIndexer
{
    public static Document fillDocument(Element element, Document document, Source source)
    {
        String documentId = element.getElementsByTag(Indexer.DOCUMENT_ID_TAG).first().text();
        document.add(new StringField(Indexer.FIELD_DOCUMENT_ID, documentId, Field.Store.YES));
        
        String documentContent = element.text();
        document.add(new TextField(Indexer.FIELD_CONTENT, documentContent, Field.Store.YES));

        return document;
    }
}
