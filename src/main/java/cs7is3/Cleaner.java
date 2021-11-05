package cs7is3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Cleaner
{
    private List<Pair<String, String>> replacements = Arrays.asList(
        new ImmutablePair<>("&(?![A-Za-z]*;)", ""), // lone '&'s that were cut off
        new ImmutablePair<>("&[A-Za-z]*;", " ")//,
        /*new ImmutablePair<>("&cent;", "c"),
        new ImmutablePair<>("&pound;", "£"),
        new ImmutablePair<>("&yen;", "¥"),
        new ImmutablePair<>("&(egs|ge);", "&gt;="),
        new ImmutablePair<>("&els;", "&lt;="),
        new ImmutablePair<>("&percnt;", "%"),
        new ImmutablePair<>("&times;", "x"),
        new ImmutablePair<>("&hyph;", "-"),
        new ImmutablePair<>("&blank;", " "),
        new ImmutablePair<>("&(a|b|B|d|e|g|G|k|l|m|N|p|r|s|t|x|z|ee|EE|kh|OH|ph|PS|th)gr;", ""),
        new ImmutablePair<>("&(ap|bull|cir|deg|mu|ohm|para|reg|rsquo|sect);", ""),
        new ImmutablePair<>("&a(acute|circ|grave|tilde|uml);", "a"),
        new ImmutablePair<>("&c(acute|cedil);", "c"),
        new ImmutablePair<>("&e(acute|grave|uml);", "e"),
        new ImmutablePair<>("&Gacute;", "G"),
        new ImmutablePair<>("&i(acute|grave|uml);", "i"),
        new ImmutablePair<>("&lacute;", "l"),
        new ImmutablePair<>("&n(acute|circ|tilde);", "n"),
        new ImmutablePair<>("&o(acute|circ|grave|tilde|uml);", "o"),
        new ImmutablePair<>("&pacute;", "p"),
        new ImmutablePair<>("&racute;", "r"),
        new ImmutablePair<>("&sacute;", "s"),
        new ImmutablePair<>("&u(acute|breve|grave|tilde|uml);", "u"),
        new ImmutablePair<>("&Agrave;", "A"),
        new ImmutablePair<>("&AElig;", "AE"),
        new ImmutablePair<>("&Ccedil;", "C"),
        new ImmutablePair<>("&E(acute|grave|uml);", "E"),
        new ImmutablePair<>("&Iuml;", "I"),
        new ImmutablePair<>("&Kuml;", "K"),
        new ImmutablePair<>("&O(acute|grave|macr|uml);", "O"),
        new ImmutablePair<>("&Ubreve;", "U")*/
    );
    private List<Pattern> patterns;

    public Cleaner()
    {
        // Compile and store regex replacment patterns
        patterns = new ArrayList<Pattern>();
        for (int i = 0; i < replacements.size(); i++)
        {
            patterns.add(Pattern.compile(replacements.get(i).getLeft()));
        }
    }

    public String clean(String string, Source source)
    {
        // Fix unquoted attribute values in FBIS files
        // i.e. '<F P=100>' becomes '<F P="100">'
        if (source == Source.FBIS)
        {
            string = string.replaceAll("<([A-Z]* [A-Z]*)=([a-zA-Z0-9\\-]*)>", "<$1=\"$2\">");
        }

        // Replace/remove instances of non-standard character entities from FBIS and FR files
        // i.e. '&pound;' becomes '£'
        if (source == Source.FBIS || source == Source.FR)
        {
            for (int i = 0; i < replacements.size(); i++)
            {
                Pattern pattern = patterns.get(i);
                String replacement = replacements.get(i).getRight();
                string = pattern.matcher(string).replaceAll(replacement);
            }
        }
        
        // Remove invalid tags from FBIS files
        // i.e. '<3>', '</3>'
        if (source == Source.FBIS)
        {
            string = string.replaceAll("</?[0-9]*>", "");
        }
        
        // Add a dummy root tag around the file to make it XML-friendly
        string = "<root>" + string + "</root>";
        
        return string;
    }
}
