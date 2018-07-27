package ch.gaps.slasher.highliter;

import org.fxmisc.richtext.model.StyleSpans;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

/**
 * This interface represents the highlighter for a CodeArea (RichTextFX class)
 */
public interface Highlighter {
    // regex used for highlighting
    // common for any SQL syntax
    String STRING_PATTERN = "'([^\"\\\\]|\\\\.)*'";
    String SEMICOLON_PATTERN = "\\;";
    String COMMENT_PATTERN = "\\-{2}[^\n]*";

    // pattern group names
    String STRING_GROUP_NAME = "STRING";
    String SEMICOLON_GROUP_NAME = "SEMICOLON";
    String COMMENT_GROUP_NAME = "COMMENT";
    String KEYWIORD_GROUP_NAME = "KEYWORD";

    /**
     * Returns the list of the sql keywords
     * @return the list of the sql keywords
     * @throws URISyntaxException if the problem occured while generating an uri of the file containing the keywords
     * @throws IOException if the problem occured while reading the content of the file containing the keywords
     */
    static List<String> getKeywords() throws URISyntaxException, IOException {
        return Files.readAllLines(Paths.get(Highlighter.class.getResource("keywords_sql_standard.txt").toURI()));
    }

    /**
     * Computes the highlighting of the text
     * @param text the string to which the highlighting is applied
     * @return the StyleSpans object containing the highlighting of the text
     */
    StyleSpans<Collection<String>> computeHighlighting(String text);

    /**
     * Returns the string representation of the css
     * @return the content of the file containing the css data.
     * If the file does not exist or the problem occurred while reading this file, returns an empty string
     */
    String getCss();
}
