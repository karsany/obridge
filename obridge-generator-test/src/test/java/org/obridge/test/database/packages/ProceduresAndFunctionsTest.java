package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;
import org.obridge.test.database.context.TestProcedure;

public class ProceduresAndFunctionsTest extends BaseTest {

    @Test
    public void testTestProcedure() throws Exception {
        TestProcedure oBridge = ProceduresAndFunctions.testProcedure("OBridge", ds);
        Assert.assertEquals("Hello OBridge", oBridge.getOut());
    }
}