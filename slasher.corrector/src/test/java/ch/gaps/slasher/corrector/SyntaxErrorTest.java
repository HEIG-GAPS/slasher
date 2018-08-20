package ch.gaps.slasher.corrector;

import ch.gaps.slasher.driver.sqlite.SQLiteCorrector;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SyntaxErrorTest {

    @Test
    public void itSouldGetIndexTillEndOfLine() {
        String sql = "SELE";
        Corrector corrector = new SQLiteCorrector();
        List<SyntaxError> errors = corrector.check(sql);
        assertEquals(1, errors.size());
        SyntaxError error = errors.get(0);
        String token = error.getOffendingToken().getText();
        assertEquals("<EOF>", token);
    }

    @Test
    public void itShouldDetectAnErrorOfFROMClause() {
        String sql = "SELECT Album AS a FRO b";
        Corrector corrector =  new SQLiteCorrector();
        List<SyntaxError> errors = corrector.check(sql);
        String token = errors.get(0).getOffendingToken().getText();
        assertEquals("FRO", token);
    }
}