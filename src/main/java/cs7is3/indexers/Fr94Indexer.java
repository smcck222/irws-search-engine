package cs7is3.indexers;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.jsoup.nodes.Element;

public class Fr94Indexer
{
    public static Document fillDocument(Element element, Document document)
    {
        String docNumber = element.getElementsByTag("DOCNO").first().text();
        document.add(new TextField("DOCNO", docNumber, Field.Store.YES));
        String text = element.getElementsByTag("TEXT").first().text().replaceAll("\n+", "\n");
        document.add(new TextField("TEXT", text, Field.Store.YES));
        return document;
    }
}
