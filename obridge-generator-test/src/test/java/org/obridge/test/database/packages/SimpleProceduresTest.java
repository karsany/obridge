package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;
import org.obridge.test.database.context.SimpleProceduresRefcursorTest;

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
}