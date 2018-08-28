package ch.gaps.slasher.corrector;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

/**
 * This class represents a syntax error detected by ANTLR4 {@link SQLParser}
 * Inspired by:
 * https://github.com/antlr/intellij-plugin-v4/blob/master/src/adaptor/org/antlr/intellij/adaptor/parser/SyntaxError.java
 */
public class SyntaxError {
    /**
     * Token that causes a syntax error
     */
    private final Token offendingToken;

    /**
     * Line of the SQL statement where the syntax error occurs
     */
    private final int line;

    /**
     * The first character of the offendingToken in the line
     */
    private final int charPositionInLine;

    /**
     * Description of the syntax error
     */
    private final String message;

    /**
     * Exception launched by the {@link SQLParser}
     */
    private final RecognitionException e;

    /**
     * Constructor
     * @param e exception launched by {@link SQLParser}
     * @param msg message containing the description of the {@link SyntaxError}
     */
    public SyntaxError(RecognitionException e, String msg) {
        offendingToken = e.getOffendingToken();
        line = e.getOffendingToken().getLine();
        charPositionInLine = e.getOffendingToken().getCharPositionInLine();
        message = msg;
        this.e = e;
    }

    /**
     * Getter
     * @return the offending {@link Token} of the syntax error
     */
    public Token getOffendingToken() {
        return offendingToken;
    }

    /**
     * Returns the length of the offending token of the {@link SyntaxError}
     * @return
     */
    public int getErrorLength() {
        return offendingToken.getText().length();
    }

    /**
     * Getter
     * @return the line of the statement in which the {@link SyntaxError} occurs
     */
    public int getLine() {
        return line;
    }

    /**
     * The position of the first character of the offending token within its line
     * @return
     */
    public int getCharPositionInLine() {
        return charPositionInLine;
    }

    /**
     * Getter
     * @return the description message of the {@link SyntaxError}
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter
     * @return the {@link RecognitionException} thrown by
     */
    public RecognitionException getException() {
        return e;
    }
}
