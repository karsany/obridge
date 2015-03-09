package org.obridge.util;

import org.junit.Assert;
import org.junit.Test;

public class StringHelperTest {

    @Test
    public void testToCamelCase() throws Exception {
        Assert.assertEquals("HelloWorld", StringHelper.toCamelCase("hello_world"));
    }

    @Test
    public void testToCamelCaseSmallBegin() throws Exception {
        Assert.assertEquals("helloWorld", StringHelper.toCamelCaseSmallBegin("hello_world"));
    }

    @Test
    public void testToOracleName() throws Exception {
        Assert.assertEquals("HELLO_WORLD", StringHelper.toOracleName("hello_world"));
        Assert.assertEquals("HELLO_WORLD", StringHelper.toOracleName("HelloWorld"));
        Assert.assertEquals("HELLO_WORLD", StringHelper.toOracleName("helloWorld"));
    }

}