package org.obridge.util;

import org.apache.commons.lang3.text.WordUtils;

public final class StringHelper {

    private StringHelper() {
    }

    public static String toCamelCase(String s) {
        if (s == null) {
            return "";
        }
        return WordUtils.capitalizeFully(s, ' ', '_').replaceAll(" ", "").replaceAll("_", "");
    }

    public static String toCamelCaseSmallBegin(String s) {
        if(s==null) {
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
}