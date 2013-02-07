// HTML5 boilerplate scripts parser (originally called ScriptsToConcat.java)
// Daniel Holth <dholth@fastmail.fm.com>, 2012
// Public Domain. http://creativecommons.org/publicdomain/zero/1.0/
// Modified by Naresh Bhatia to generalize filename extraction from different comment blocks

import java.io.FileReader;
import java.io.IOException;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTML.Attribute;;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

/**
 * Parse an HTML file, printing all occurrences of the specified attribute for
 * the specified tag when it occurs between the following comments:
 * <!-- //-beg- section_name -->
 * <!-- //-end- section_name -->
 * 
 * Example:
 * <!-- //-beg- concatenate_scripts -->
 * <script src="js/libs/jquery-1.8.0.js"></script>
 * <script src="js/libs/jquery-ui-1.8.20.custom.min.js"></script>
 * <!-- //-end- concatenate_scripts -->
 *
 * To extract the "src" attribute for all "script" tags in the
 * "concatenate_scripts" section, call FindAttribute as follows:
 * 
 * FindAttribute index.html concatenate_scripts script src
 */
public class FindAttribute extends ParserCallback {
    private String begComment;
    private String endComment;
    private Tag tag;
    private Attribute attribute;
    private boolean emitting = false;
    
    public FindAttribute(String sectionName, String tagName, String attributeName) {
        this.begComment = "//-beg- " + sectionName;
        this.endComment = "//-end- " + sectionName;
        this.tag = HTML.getTag(tagName);
        this.attribute = HTML.getAttributeKey(attributeName);
    }

    @Override
    public void handleComment(char[] data, int pos) {
        String text = new String(data);
        if (text.indexOf(this.begComment) >= 0) {
            emitting = true;
        }
        else if (text.indexOf(this.endComment) >= 0) {
            emitting = false;
        }
    }

    @Override
    public void handleSimpleTag(Tag t, MutableAttributeSet a, int pos) {
        this.handleTag(t, a, pos);
    }

    @Override
    public void handleStartTag(Tag t, MutableAttributeSet a, int pos) {
        this.handleTag(t, a, pos);
    }
    
    // Handles both simple and start tags
    private void handleTag(Tag t, MutableAttributeSet a, int pos) {
        if (emitting && t == this.tag) {
            String attributeValue =
                a.getAttribute(this.attribute).toString();
            if (attributeValue != null) {
                System.out.println(attributeValue);
            }
        }
    }

    public static void main(String args[]) throws IOException {
        if (args.length != 4) {
            System.err.println("usage: FindAttribute htmlFilename sectionName tagName attributeName");
            System.exit(1);
        }
        FileReader fr = new FileReader(args[0]);
        new ParserDelegator().parse(
                fr, new FindAttribute(args[1], args[2], args[3]), true);
    }
}
