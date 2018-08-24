package ch.gaps.slasher.corrector;

public interface ParserError {
    int getLine();
    int getCharPositionInLine();
    String getMessage();
}
