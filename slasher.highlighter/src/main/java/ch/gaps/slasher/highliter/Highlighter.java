package ch.gaps.slasher.highliter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

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
    String KEYWORD_GROUP_NAME = "KEYWORD";

    /**
     * Returns the list of the sql keywords
     * @return the list of the sql keywords
     * @throws URISyntaxException if the problem occured while generating an uri of the file containing the keywords
     * @throws IOException if the problem occured while reading the content of the file containing the keywords
     */
    default List<String> getKeywords() throws URISyntaxException, IOException {
        return Files.readAllLines(Paths.get(Highlighter.class.getResource("sql2003_keywords.txt").toURI()));
    }

    /**
     * Return the
     * Important note: CSS style class is the matcher group name in lower case
     * @return the matcher group names
     */
    List<String> getMatcherGroupNames();

    /**
     * String Pattern fot the given SQL syntax
     * @return
     */
    Pattern getPattern();
}
