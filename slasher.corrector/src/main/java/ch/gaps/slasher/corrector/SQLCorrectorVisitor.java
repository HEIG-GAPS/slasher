package ch.gaps.slasher.corrector;

import org.antlr.v4.runtime.RecognitionException;

import java.util.ArrayList;
import java.util.List;

public class SQLCorrectorVisitor extends SQLParserBaseVisitor<List<SyntaxError>> {
    private List<SyntaxError> errors = new ArrayList<SyntaxError>();

    @Override
    public List<SyntaxError> visitSql(SQLParser.SqlContext ctx) {
        RecognitionException ex = ctx.exception;
        if (ex != null) {
            errors.add(new SyntaxError(ctx.exception, "syntax error"));
            return errors;
        }
        return null;
    }
}
