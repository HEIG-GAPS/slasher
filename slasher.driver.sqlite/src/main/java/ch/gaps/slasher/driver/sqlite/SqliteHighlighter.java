package ch.gaps.slasher.driver.sqlite;

import ch.gaps.slasher.highliter.Highlighter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class represents a highliter for the Sqlite syntax
 */
public class SqliteHighlighter implements Highlighter {

    // regex used for highlighting
    // specific to SQLite syntax
    private static final String QUOTE_ID_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACKET_ID_PATTERN = "\\[([^\"\\\\]|\\\\.)*\\]";

    // pattern group names
    private static final String QUOTE_ID_GROUP_NAME = "QUOTEID";
    private static final String PAREN_GROUP_NAME = "PAREN";
    private static final String BRACKET_ID_GROUP_NAME = "BRACKETID";


    private List<String> keywords;
    private String keywordPattern;
    private Pattern pattern;

    public SqliteHighlighter() throws IOException, URISyntaxException {
        keywords = getKeywords();
        keywordPattern = "\\b(" + String.join("|", keywords) + ")\\b";
        String regex = "(?<" + KEYWORD_GROUP_NAME + ">"+ keywordPattern + ")"
                + "|(?<" + STRING_GROUP_NAME + ">" + STRING_PATTERN + ")"
                + "|(?<" + PAREN_GROUP_NAME + ">" + PAREN_PATTERN + ")"
                + "|(?<" + SEMICOLON_GROUP_NAME+ ">" + SEMICOLON_PATTERN + ")"
                + "|(?<" + COMMENT_GROUP_NAME+ ">" + COMMENT_PATTERN + ")"
                + "|(?<" + QUOTE_ID_GROUP_NAME + ">" + QUOTE_ID_PATTERN + ")"
                + "|(?<" + BRACKET_ID_GROUP_NAME + ">" + BRACKET_ID_PATTERN + ")";

        pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    @Override
    public List<String> getKeywords() throws URISyntaxException, IOException {
        return Files.readAllLines(Paths.get(
          SqliteHighlighter.class.getResource("/keywords_sqlite.txt").toURI()));
    }

    public List<String> getMatcherGroupNames() {
        String[] groupNames = {
                KEYWORD_GROUP_NAME,
                PAREN_GROUP_NAME,
                STRING_GROUP_NAME,
                SEMICOLON_GROUP_NAME,
                COMMENT_GROUP_NAME,
                QUOTE_ID_GROUP_NAME,
                BRACKET_ID_GROUP_NAME
        };

        return new ArrayList<>(Arrays.asList(groupNames));
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
