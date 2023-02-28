package org.obridge.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.obridge.model.generator.Pojo;
import org.obridge.model.generator.PojoField;

import java.util.ArrayList;

public class MustacheRunnerTest {


    public static final String COMMENT = "This is a comment";
    public static final String PACKAGE_NAME = "hu.karsany.testpackage";
    private final static String CLASS_NAME = "ExampleClass";

    private Pojo pojo;

    @BeforeEach
    public void fillPojo() {
        pojo = new Pojo();
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

        pojo.setImports(new ArrayList<>());
        pojo.getImports().add("java.math.BigDecimal");
    }

    @Test
    public void pojoMustacheTest() {
        String s = MustacheRunner.build("pojo.mustache", pojo);

        Assertions.assertTrue(s.contains(CLASS_NAME));
        Assertions.assertTrue(s.contains(COMMENT));
        Assertions.assertTrue(s.contains("package " + PACKAGE_NAME));
        Assertions.assertTrue(s.contains("getField1"));
        Assertions.assertTrue(s.contains("setField1"));
        Assertions.assertTrue(s.contains("getField2"));
        Assertions.assertFalse(s.contains("setField2"));
    }
}
