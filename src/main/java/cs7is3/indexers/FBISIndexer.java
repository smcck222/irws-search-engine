package cs7is3.indexers;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.jsoup.nodes.Element;

import cs7is3.Indexer;

public class FBISIndexer
{
    public static Document fillDocument(Element element, Document document)
    {
        
        String docNumber = element.getElementsByTag(Indexer.DOCUMENT_ID_TAG).text();
        document.add(new StringField(Indexer.FIELD_DOCUMENT_ID, docNumber, Field.Store.YES));

        String documentContent = element.text();
        document.add(new TextField(Indexer.FIELD_CONTENT, documentContent, Field.Store.YES));
        
        String mainTitle = element.getElementsByTag("TI").text();
        document.add(new TextField(Indexer.FIELD_TITLE, mainTitle, Field.Store.YES));
        // Look for other titles/headings in other <H> tags. <TI> is inside <H3>

        String allText = element.getElementsByTag("TEXT").text();
        document.add(new TextField("TEXT", allText, Field.Store.YES));
        // <F P=..>  tags might be after <HEADER> and/or in <TEXT>. Strip them from there?

        String date = element.getElementsByTag("DATE1").text();
        document.add(new TextField("DATE", date, Field.Store.YES));
        // How is this date different from the <F P=102> date?

        if (element.getElementsByTag("F") != null) {

            //All F Tags
            //String fullExtraFields = element.getElementsByTag("F").text();
            //document.add(new StringField("INFO", fullExtraFields, Field.Store.YES));

            List<Element> fTags = element.getElementsByTag("F");
            for (Element e : fTags) {
                
                if (e.attr("P").equals("105")) {
                    String language = e.text();
                    document.add(new StringField("LANGUAGE", language, Field.Store.YES));
                }
                else if (e.attr("P").equals("100")) {
                    String region = e.text();
                    document.add(new StringField("REGION", region, Field.Store.YES));
                }
                else if (e.attr("P").equals("101")) {
                    String location = e.text();
                    document.add(new TextField(Indexer.FIELD_LOCATION, location, Field.Store.YES));
                }
                else if (e.attr("P").equals("104")) {
                    String network = e.text();
                    document.add(new StringField("NETWORK", network, Field.Store.YES));
                }
                else if (e.attr("P").equals("106")) {
                    String reporter = e.text();
                    // [Report by E. van Wyk] => take only the name?
                    document.add(new StringField("REPORTER", reporter, Field.Store.YES));
                }
            }
        }

        //<F P=..> random information, has location, broadcast network, etc.
        // EA2802134094 Asmara Voice of the Broad Masses of Eritrea in Tigrinya 0400 GMT 28 Feb 94
        //<F P=100> is region.
        //<F P=101> is location. 
        //<F P=104> is tv/radio network.
        //<F P=105> is language.
        //<F P=106> is reporter.
        
        // <HEADER> (might) have lone text "Article Type" and "Document Type", is that useful?
        
        /* 
        <HEADER>
        <DATE1>   23 March 1994 </DATE1>
        Article Type:FBIS 
        Document Type:FOREIGN MEDIA NOTE--FB PN 94-036--JAPAN 
        <H3> <TI>      JAPAN:  SPOTLIGHT ON JAPAN ASSOCIATION OF DEFENSE INDUSTRY </TI></H3>
        </HEADER>
        */
        
        //document.add(new TextField("content", element.text(), Field.Store.YES));
        return document;
    }
}
