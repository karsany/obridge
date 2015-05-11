package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;
import org.obridge.test.database.context.*;
import org.obridge.test.database.objects.SampleTypeOne;
import org.obridge.test.database.objects.TestPackageLocTestType;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    @Test
    public void testObjectType() throws SQLException {
        TestPackageObjectTypeTest1 ctx = new TestPackageObjectTypeTest1();

        SampleTypeOne objectType = new SampleTypeOne();
        objectType.setAttrInt(3);
        ctx.setObjectType(objectType);

        TestPackage.objectTypeTest1(ctx, ds.getConnection());

        Assert.assertEquals(3, ctx.getFunctionReturn().intValue());
        Assert.assertEquals("Hello!", ctx.getObjectType().getAttrVarchar());
    }

    @Test
    public void testTableType() {
        TestPackageTableOfTest1 ctx = TestPackage.tableOfTest1(new ArrayList<SampleTypeOne>(), ds);
        Assert.assertEquals(1, ctx.getFunctionReturn().intValue());
        Assert.assertEquals("Hello!", ctx.getListOf().get(0).getAttrVarchar());
    }


    @Test
    public void testAllTypes() throws SQLException {
        TestPackageAllTypes ctx = new TestPackageAllTypes();
        TestPackage.allTypes(ctx, ds.getConnection());
    }

    @Test
    public void testQuotationMarks1() {
        TestPackage.quotedProcedureName(ds);
        Assert.assertTrue(TestPackage.simpleBooleanReturn(ds).getFunctionReturn());
    }

    @Test
    public void testStringList1() {
        TestPackageReturnStringList testPackageReturnStringList = TestPackage.returnStringList(ds);
        Assert.assertEquals(3, testPackageReturnStringList.getFunctionReturn().size());
    }

    @Test
    public void testNumberListAsInput1() {
        List<Integer> integers = new ArrayList<Integer>();
        integers.add(3);
        integers.add(10);
        integers.add(0);
        TestPackageSumList testPackageSumList = TestPackage.sumList(integers, ds);
        Assert.assertTrue(testPackageSumList.getFunctionReturn().equals(new BigDecimal(13)));
    }

    @Test
    public void testGetLocTestType() {
        TestPackageLocTestType testType = new TestPackageLocTestType();
        testType.setName("OBRIDGE");
        testType.setId(new BigDecimal(1));
        TestPackageGetLocTestType locTestType = TestPackage.getLocTestType(testType, ds);
        Assert.assertEquals("OBRIDGEABC", locTestType.getTp().getName());
        Assert.assertEquals(new BigDecimal(2), locTestType.getTp().getId());
    }

}