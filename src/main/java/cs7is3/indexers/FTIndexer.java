package cs7is3.indexers;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.jsoup.nodes.Element;

public class FTIndexer
{
    public static Document fillDocument(Element element, Document document)
    {

        // <DOCNO>FT911-1</DOCNO>
        // <PROFILE>_AN-BENBQAD8FT</PROFILE>
        // <DATE>910514 </DATE>
        // <HEADLINE>FT  14 MAY 91 / (CORRECTED) Jubilee of a jet...</HEADLINE>
        // <TEXT>Correction (published 16th May 1991) appended to this...</TEXT>
        // <PUB>The Financial Times</PUB>
        // <PAGE>London Page 7 Photograph (Omitted).</PAGE>

        String docNo = element.getElementsByTag("DOCNO").text();
        document.add(new StringField("document number", docNo, Field.Store.YES));

        String profile = element.getElementsByTag("PROFILE").text();
        document.add(new TextField("profile", profile, Field.Store.YES));
        
        String date = element.getElementsByTag("DATE").text();
        document.add(new TextField("date", date, Field.Store.YES));
        
        String headline = element.getElementsByTag("HEADLINE").text();
        document.add(new TextField("headline", headline, Field.Store.YES));

        String text = element.getElementsByTag("TEXT").text();
        document.add(new TextField("text", text, Field.Store.YES));
        
        String pub = element.getElementsByTag("PUB").text();
        document.add(new TextField("publication", pub, Field.Store.YES));
        
        String page = element.getElementsByTag("PAGE").text();
        document.add(new TextField("page", page, Field.Store.YES));

        // if (docNo.equals("FT922-746")) 
        //     System.out.println(document.toString());

        return document;
    }
}
