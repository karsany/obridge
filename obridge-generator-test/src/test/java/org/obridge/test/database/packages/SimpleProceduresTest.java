package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;
import org.obridge.test.database.context.SimpleProceduresRawInTypeTest;
import org.obridge.test.database.context.SimpleProceduresRawTest;
import org.obridge.test.database.context.SimpleProceduresRefcursorTest;
import org.obridge.test.database.context.SimpleProceduresTestTypeWithIntegerField;

import java.sql.Connection;
import java.sql.SQLException;

public class SimpleProceduresTest extends BaseTest {

    @Test
    public void testRefcursorTest() throws SQLException {

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

    @Test
    public void testRaise() {
        try {
            SimpleProcedures.raiseError(ds);
            Assert.assertFalse("should not be here", true);
        } catch (RuntimeException e) {
            Assert.assertTrue("Throwed exception", e.getStackTrace()[0].getClassName().contains("StoredProcedureCallException"));
        }

    }

    @Test
    public void testRawTest() {
        SimpleProceduresRawTest simpleProceduresRawTest = SimpleProcedures.rawTest(new byte[]{3, 2, 1, 0}, ds);
        Assert.assertEquals(4, simpleProceduresRawTest.getR().length);
        Assert.assertEquals(1, simpleProceduresRawTest.getR()[2]);
    }

    @Test
    public void testRawInTypeTest() {
        SimpleProceduresRawInTypeTest simpleProceduresRawInTypeTest = SimpleProcedures.rawInTypeTest(ds);
        System.out.println();

        Assert.assertEquals(5, simpleProceduresRawInTypeTest.getTt().getRawCol().length);
        Assert.assertEquals('l', simpleProceduresRawInTypeTest.getTt().getRawCol()[2]);
        Assert.assertEquals('l', simpleProceduresRawInTypeTest.getTt().getRawCol()[3]);
        Assert.assertEquals('o', simpleProceduresRawInTypeTest.getTt().getRawCol()[4]);
    }


}