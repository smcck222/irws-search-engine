package cs7is3;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.w3c.dom.Node;

public class Fr94Indexer {

    public Document fillDocument(Node xml, Document document) {
        document.add(new TextField("content", xml.getTextContent(), Field.Store.YES));
        return document;
    }
}
