/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Ferenc Karsany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package org.obridge.util;

import java.util.List;

/**
 * @author fkarsany
 */
public class Joiner {

    private String splitSymbol;
    private boolean skipNulls = false;

    public static Joiner on(String splitSymbol) {
        Joiner joiner = new Joiner();
        joiner.setSplitSymbol(splitSymbol);
        return joiner;
    }

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
