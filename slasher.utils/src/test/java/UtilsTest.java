import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    @Test
    public void calculateOffsetShouldWorkOnASingleLineWithoutLineSeparator() {
        String text = "yo intento a aprender espanol";
        int actual = Utils.calculateOffset(text, 1, 3);
        assertEquals(3, actual);
    }

    @Test
    public void calculateOffsetShouldWorkOnASingleLineWithLineSeparator() {
        String text = "yo intento a aprender espanol\n";
        int actual = Utils.calculateOffset(text, 0, 0);
        assertEquals(0, actual);
    }

    @Test
    public void calculateOffsetShouldWorkWithMultipleLines() {
        String text = "yo intento a aprender espanol\n"
                + "pero no tengo practica";
        int actual = Utils.calculateOffset(text, 2, 3);
        assertEquals(33, actual);
    }
}