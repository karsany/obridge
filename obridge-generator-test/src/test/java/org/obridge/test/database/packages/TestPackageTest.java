package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;
import org.obridge.test.database.context.*;
import org.obridge.test.database.objects.SampleTypeLists;
import org.obridge.test.database.objects.SampleTypeOne;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    public void testSampleTypeLists() throws ParseException {

        SampleTypeLists sampleTypeLists = new SampleTypeLists();
        sampleTypeLists.setList4(Arrays.asList("a", "b", "c"));
        sampleTypeLists.setList5(Arrays.asList(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ZERO));
        sampleTypeLists.setList6(Arrays.asList(1, 2, 3));
        TestPackageTestManyNameList testPackageTestManyNameList = TestPackage.testManyNameList(sampleTypeLists, ds);
        Assert.assertEquals("TEST", testPackageTestManyNameList.getTp().getList4().get(3));
        Assert.assertEquals(new BigDecimal(3.5), testPackageTestManyNameList.getTp().getList5().get(3));
        Assert.assertEquals(new Integer(28), testPackageTestManyNameList.getTp().getList6().get(3));
        Assert.assertEquals(new java.sql.Date(new SimpleDateFormat("yyyyMMdd").parse("20000101").getTime()), testPackageTestManyNameList.getTp().getList7().get(0));

    }

}