package org.obridge.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.text.WordUtils;

public final class StringHelper {

    private static Set<String> javaKeywords = new HashSet<String>(Arrays.asList("abstract", "continue", "for", "new",
            "switch", "assert", "default", "goto", "package", "synchronized",
            "boolean", "do", "if", "private", "this", "break", "double",
            "implements", "protected", "throw", "byte", "else", "import",
            "public", "throws", "case", "enum", "instanceof", "return",
            "transient", "catch", "extends", "int", "short", "try", "char",
            "final", "interface", "static", "void", "class", "finally", "long",
            "strictfp", "volatile", "const", "float", "native", "super", "while"));

    private StringHelper() {
    }

    public static String toCamelCase(String s) {
        if (s == null) {
            return "";
        }
        return WordUtils.capitalizeFully(s, ' ', '_').replaceAll(" ", "").replaceAll("_", "");
    }

    public static String toCamelCaseSmallBegin(String s) {
        if (s == null) {
            return "";
        }
        String ss = toCamelCase(s);
        return ss.substring(0, 1).toLowerCase() + ss.substring(1);
    }

    public static String toOracleName(String s) {
        StringBuilder result = new StringBuilder();
        String currChar;
        if (s != null && !s.isEmpty()) {
            for (int i = 0; i < s.length(); i++) {
                currChar = s.substring(i, i + 1);
                if (i != 0 && currChar.equals(currChar.toUpperCase())) {
                    result.append("_" + currChar);
                } else {
                    result.append(currChar);
                }
            }
        }
        return result.toString().toUpperCase().replaceAll("\\_\\_", "_");
    }

    public static boolean isJavaKeyword(String s) {
        return javaKeywords.contains(s);
    }

    public static String unJavaKeyword(String s) {
        if (isJavaKeyword(s)) {
            return "p" + s.substring(0, 1).toUpperCase() + s.substring(1);
        }
        return s;
    }

}
