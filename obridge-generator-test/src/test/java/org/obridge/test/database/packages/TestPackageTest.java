package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;

import java.util.Date;

public class TestPackageTest extends BaseTest {

    @Test
    public void testGetSysdate1() throws Exception {
        java.sql.Date expected = new java.sql.Date(new Date().getTime());
        java.sql.Date actual = TestPackage.getSysdate1(ds).getFunctionReturn();
        Assert.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testGetSysdate2() throws Exception {
        java.sql.Date expected = new java.sql.Date(new Date().getTime());
        java.sql.Date actual = TestPackage.getSysdate2(ds).getSysdate();
        Assert.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void testHelloWorld() throws Exception {
        final String name = "FERKO";
        Assert.assertEquals("Hello " + name + "!", TestPackage.helloWorld(name, ds).getOut());
    }
}