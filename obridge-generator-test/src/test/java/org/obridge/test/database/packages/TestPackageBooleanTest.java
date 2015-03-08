package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;
import org.obridge.test.database.context.TestPackageBooleanTest2;

import java.math.BigDecimal;
import java.sql.SQLException;

public class TestPackageBooleanTest extends BaseTest {

    @Test
    public void testBooleanTest1() {

        Assert.assertEquals("TRUE", TestPackage.booleanTest1(true, ds).getFunctionReturn());
        Assert.assertEquals("FALSE", TestPackage.booleanTest1(false, ds).getFunctionReturn());
        Assert.assertEquals("NULL", TestPackage.booleanTest1(null, ds).getFunctionReturn());

    }

    @Test
    public void testBooleanTest2() throws SQLException // throws Exception
    {
        TestPackageBooleanTest2 ctx = new TestPackageBooleanTest2();
        ctx.setN(BigDecimal.ZERO);
        ctx.setBoolIn(true);
        ctx.setBoolInout(false);
        TestPackage.booleanTest2(ctx, ds.getConnection());
        Assert.assertEquals(BigDecimal.ONE, ctx.getN());
        Assert.assertEquals(true, ctx.getBoolOut());
        Assert.assertEquals(true, ctx.getBoolInout());

    }
}