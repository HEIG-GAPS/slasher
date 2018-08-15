package ch.gaps.slasher.corrector;

import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

/**
 * https://github.com/antlr/intellij-plugin-v4/blob/master/src/adaptor/org/antlr/intellij/adaptor/parser/SyntaxError.java
 * modified
 */
public class SyntaxError {
    private final Token offendingToken;
    private final int line;
    private final int charPositionInLine;
    private final String message;
    private final RecognitionException e;

    public SyntaxError(RecognitionException e, String msg) {
        offendingToken = e.getOffendingToken();
        line = e.getOffendingToken().getLine();
        charPositionInLine = e.getOffendingToken().getCharPositionInLine();
        message = msg;
        this.e = e;
    }

    public Token getOffendingSymbol() {
        return offendingToken;
    }

    public int getErrorLength() {
        return offendingToken.getText().length();
    }

    public int getLine() {
        return line;
    }

    public int getCharPositionInLine() {
        return charPositionInLine;
    }

    public String getMessage() {
        return message;
    }

    public RecognitionException getException() {
        return e;
    }
}
