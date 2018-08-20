package ch.gaps.slasher.utils;

public class Utils {
    /**
     * Transforms the line and character position of the character in the string
     * to the absolute character position (offset) in this string
     * @param text the string in which the character position is given
     * @param line line index of the concerned character (starting with line 1)
     * @param charPos position of the character in this line
     * @return the offset position of the given character
     */
    public static int calculateOffset(String text, int line, int charPos) {
        String[] lines = text.split(System.lineSeparator());
        int offset = 0;
        int lineCnt = 0;
        for (String l : lines) {
            lineCnt++;
            if (lineCnt < line) {
                offset += l.length() + System.lineSeparator().length();
            }
        }
        offset += charPos;

        return offset;
    }

    public static int[] getLineIndexes(String text, int line) {
        int[] lineIndexes = new int[2];
        if (text.isEmpty()) {
            return lineIndexes;
        }
        int curIndex = 0;
        int curLine = 1;
        char curChar;
        while (curIndex < text.length() && curLine < line) {
            curChar = text.charAt(curIndex);
            if (curChar == '\n') {
                curLine++;
            }
            curIndex++;
        }
        if (curIndex == text.length()) {
            return null;
        }
        lineIndexes[0] = curIndex;
        curChar = text.charAt(curIndex);
        while (curIndex < text.length()) {
            curChar = text.charAt(curIndex);
            curIndex++;
            if (curChar == '\n') {
                break;
            }
        }
        lineIndexes[1] = curIndex;
        return lineIndexes;
    }
}
