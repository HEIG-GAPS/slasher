package ch.gaps.slasher.corrector;

import org.antlr.v4.runtime.RecognitionException;
import java.util.ArrayList;
import java.util.List;

public class SQLCorrectorVisitor extends SQLParserBaseVisitor<List<SyntaxError>> {
    private List<SyntaxError> errors = new ArrayList<>();

    @Override
    public List<SyntaxError> visitSql(SQLParser.SqlContext ctx) {
        RecognitionException ex = ctx.exception;
        if (ex != null) {
            errors.add(new SyntaxError(ctx.exception, "syntax error"));
        }
        visitChildren(ctx);
        return errors;
    }

    @Override
    public List<SyntaxError> visitStatement(SQLParser.StatementContext ctx) {
        visitChildren(ctx);
        return errors;
    }

    @Override
    public List<SyntaxError> visitData_statement(SQLParser.Data_statementContext ctx) {
        visitChildren(ctx);
        return errors;
    }

    @Override
    public List<SyntaxError> visitQuery_expression(SQLParser.Query_expressionContext ctx) {
        visitChildren(ctx);
        return errors;
    }

    @Override
    public List<SyntaxError> visitQuery_expression_body(SQLParser.Query_expression_bodyContext ctx) {
        visitChildren(ctx);
        return errors;
    }

    @Override
    public List<SyntaxError> visitNon_join_query_expression(SQLParser.Non_join_query_expressionContext ctx) {
        visitChildren(ctx);
        return errors;
    }

    @Override
    public List<SyntaxError> visitNon_join_query_term(SQLParser.Non_join_query_termContext ctx) {
        visitChildren(ctx);
        return errors;
    }

    @Override
    public List<SyntaxError> visitNon_join_query_primary(SQLParser.Non_join_query_primaryContext ctx) {
        visitChildren(ctx);
        return errors;
    }

    @Override
    public List<SyntaxError> visitSimple_table(SQLParser.Simple_tableContext ctx) {
        visitChildren(ctx);
        return errors;
    }

    @Override
    public List<SyntaxError> visitQuery_specification(SQLParser.Query_specificationContext ctx) {
        visitChildren(ctx);
        return errors;
    }

    public List<SyntaxError> getErrors() {
        return errors;
    }
}
