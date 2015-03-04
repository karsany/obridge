package org.obridge.util;

import java.util.List;

/**
 * @author fkarsany
 */
public class Joiner {

    private String splitSymbol;
    private boolean skipNulls = false;

    public boolean isSkipNulls() {
        return skipNulls;
    }

    public void setSkipNulls(boolean skipNulls) {
        this.skipNulls = skipNulls;
    }

    public String getSplitSymbol() {
        return splitSymbol;
    }

    public void setSplitSymbol(String splitSymbol) {
        this.splitSymbol = splitSymbol;
    }

    public static Joiner on(String splitSymbol) {
        Joiner joiner = new Joiner();
        joiner.setSplitSymbol(splitSymbol);
        return joiner;
    }

    public Joiner skipNulls() {
        this.setSkipNulls(true);
        return this;
    }

    public String join(List<String> strings) {

        StringBuilder sb = new StringBuilder();

        int j = 0;
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            if (!skipNulls || (s != null && !s.isEmpty())) {
                if (j != 0) {
                    sb.append(this.splitSymbol);
                }
                if (s != null && !s.isEmpty()) {
                    sb.append(s);
                }
                j++;

            }
        }

        return sb.toString();
    }

}
