package org.obridge.util;

import com.github.abrarsyed.jastyle.ASFormatter;
import com.github.abrarsyed.jastyle.FormatterHelper;
import com.github.abrarsyed.jastyle.constants.EnumFormatStyle;

import java.io.StringReader;

/**
 * Created by fkarsany on 2015.03.11..
 */
public class CodeFormatter {

    public static String format(String converterString) {
        ASFormatter formatter = new ASFormatter();
        formatter.setFormattingStyle(EnumFormatStyle.JAVA);
        formatter.setDeleteEmptyLinesMode(true);
        converterString = FormatterHelper.format(new StringReader(converterString), formatter);
        return converterString;
    }
}
