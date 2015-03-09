package org.obridge.util;


import org.junit.Assert;
import org.junit.Test;
import org.obridge.model.generator.Pojo;
import org.obridge.model.generator.PojoField;

import java.util.ArrayList;

public class MustacheRunnerTest {

    public static final String COMMENT = "This is a comment";
    public static final String PACKAGE_NAME = "hu.karsany.tesztpackage";
    private final static String CLASS_NAME = "ExampleClass";

    @Test
    public void pojoMustacheTest() throws Exception {

        Pojo pojo = new Pojo();
        pojo.setClassName(CLASS_NAME);
        pojo.setComment(COMMENT);
        pojo.setPackageName(PACKAGE_NAME);
        pojo.setFields(new ArrayList<PojoField>());

        PojoField f1 = new PojoField();
        f1.setFieldType("String");
        f1.setFieldName("field1");
        f1.setReadonly(false);
        pojo.getFields().add(f1);

        PojoField f2 = new PojoField();
        f2.setFieldType("String");
        f2.setFieldName("field2");
        f2.setReadonly(true);
        pojo.getFields().add(f2);

        pojo.setImports(new ArrayList<String>());
        pojo.getImports().add("java.math.BigDecimal");

        String s = MustacheRunner.build("pojo.mustache", pojo);

        Assert.assertTrue(s.contains(CLASS_NAME));
        Assert.assertTrue(s.contains(COMMENT));
        Assert.assertTrue(s.contains("package " + PACKAGE_NAME));
        Assert.assertTrue(s.contains("getField1"));
        Assert.assertTrue(s.contains("setField1"));
        Assert.assertTrue(s.contains("getField2"));
        Assert.assertFalse(s.contains("setField2"));

    }
}