package org.obridge.util;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by fkarsany on 2015.01.03..
 */
public final class MustacheRunner {

    private MustacheRunner() {

    }

    public static String build(String templateName, Object backingObject) {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(templateName);
        Writer execute = mustache.execute(new StringWriter(), backingObject);
        return execute.toString();
    }

}
