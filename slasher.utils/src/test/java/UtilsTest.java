import ch.gaps.slasher.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    @Test
    void calculateOffsetShouldWorkOnASingleLineWithoutLineSeparator() {
        String text = "yo intento a aprender espanol";
        int actual = Utils.calculateOffset(text, 1, 3);
        assertEquals(3, actual);
    }

    @Test
    void calculateOffsetShouldWorkOnASingleLineWithLineSeparator() {
        String text = "yo intento a aprender espanol\n";
        int actual = Utils.calculateOffset(text, 0, 0);
        assertEquals(0, actual);
    }

    @Test
    void calculateOffsetShouldWorkWithMultipleLines() {
        String text = "yo intento a aprender espanol\n"
                + "pero no tengo practica";
        int actual = Utils.calculateOffset(text, 2, 3);
        assertEquals(33, actual);
    }

    @Test
    void getLineIndexesShouldWorkWithEmptyLine() {
        int[] actual = Utils.getLineIndexes("", 1);
        int[] expected = new int[2];
        assertArrayEquals(expected, actual);
    }

    @Test
    void getLineINdexesShouldWorkWithSingleLineContainingNewline() {
        int[] actual = Utils.getLineIndexes("\n", 1);
        int[] expected = new int[2];
        expected[0] = 0;
        expected[1] = 1;
        assertArrayEquals(expected, actual);
    }

    @Test
    void getLineIndexesShouldWorkWithMultipleLinesContainingNewlines() {
        int[] actual = Utils.getLineIndexes("\n\n", 2);
        int[] expected = new int[2];
        expected[0] = 1;
        expected[1] = 2;
        assertArrayEquals(expected, actual);

        actual = Utils.getLineIndexes("\n\n", 1);
        expected[0] = 0;
        expected[1] = 1;
        assertArrayEquals(expected, actual);
    }

    @Test
    void getLineIndexesShouldWorkWithOneLine() {
        int[] actual = Utils.getLineIndexes("abc", 1);
        int[] expected = new int[2];
        expected[1] = 3;
        assertArrayEquals(expected, actual);
    }

    @Test
    void getLineIndexesShouldWorkWithMultipleLines() {
        int[] actual  = Utils.getLineIndexes("abc\ndef", 2);
        int[] expected = new int[2];
        expected[0] = 4;
        expected[1] = 7;
        assertArrayEquals(expected, actual);
    }
}