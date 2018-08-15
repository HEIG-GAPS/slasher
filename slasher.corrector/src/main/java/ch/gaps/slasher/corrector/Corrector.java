package ch.gaps.slasher.corrector;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;

public interface Corrector {
    default public List<SyntaxError> check(String statements) {
        SQLLexer lexer = new SQLLexer(CharStreams.fromString(statements));
        SQLParser parser = new SQLParser(new CommonTokenStream(lexer));

        ParseTree parseTree = parser.sql();
        SQLCorrectorVisitor visitor = new SQLCorrectorVisitor();
        return visitor.visit(parseTree);
    }
}
