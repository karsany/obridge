package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;
import org.obridge.test.database.context.SimpleProceduresRefcursorTest;
import org.obridge.test.database.context.SimpleProceduresTestTypeWithIntegerField;

import java.sql.Connection;

public class SimpleProceduresTest extends BaseTest {

    @Test
    public void testRefcursorTest() throws Exception {

        Connection connection = this.ds.getConnection();
        SimpleProceduresRefcursorTest ctx = SimpleProcedures.refcursorTest(connection);
        Assert.assertFalse(ctx.getRefc().isClosed());
        ctx.getRefc().next();
        Assert.assertEquals("X", ctx.getRefc().getString(1));
        connection.close();

    }

    @Test
    public void testTypeWithInteger() {
        SimpleProceduresTestTypeWithIntegerField ret = SimpleProcedures.testTypeWithIntegerField(null, this.ds);
        Assert.assertEquals(new Integer(1), ret.getTp().getField1());
        Assert.assertEquals("ABC", ret.getTp().getField2());

        ret = SimpleProcedures.testTypeWithIntegerField(ret.getTp(), this.ds);

        Assert.assertEquals(new Integer(2), ret.getTp().getField1());
        Assert.assertEquals("ABCABC", ret.getTp().getField2());

        Assert.assertEquals("class java.lang.Integer", ret.getTp().getField1().getClass().toString());

    }


}