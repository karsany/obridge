package org.obridge.util;

import com.github.abrarsyed.jastyle.ASFormatter;
import com.github.abrarsyed.jastyle.FormatterHelper;
import com.github.abrarsyed.jastyle.constants.EnumFormatStyle;

import java.io.StringReader;

/**
 * Created by fkarsany on 2015.03.11..
 */
public final class CodeFormatter {

    private CodeFormatter() {
    }

    public static String format(String javaSource) {
        ASFormatter formatter = new ASFormatter();
        formatter.setFormattingStyle(EnumFormatStyle.JAVA);
        formatter.setDeleteEmptyLinesMode(true);
        return FormatterHelper.format(new StringReader(javaSource), formatter);
    }
}
