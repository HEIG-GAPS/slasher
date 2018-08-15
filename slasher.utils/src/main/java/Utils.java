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
}
