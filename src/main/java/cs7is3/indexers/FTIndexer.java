package cs7is3.indexers;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.jsoup.nodes.Element;

public class FTIndexer
{
    public static Document fillDocument(Element element, Document document)
    {
        document.add(new TextField("content", element.text(), Field.Store.YES));
        return document;
    }
}
