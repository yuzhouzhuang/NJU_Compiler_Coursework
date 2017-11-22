package lab1.Property;

public class Type {
    private static String[] keywords = new String[]{
            "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public", "return", "strictfp", "short", "static", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while"
    };

    public static boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    public static boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    public static boolean isKeyword(String s) {
        for (String string : keywords) {
            if (string.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
