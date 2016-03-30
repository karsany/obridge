package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;
import org.obridge.test.database.context.Execfunction;
import org.obridge.test.database.context.TestProcedure;

import java.math.BigDecimal;

public class ProceduresAndFunctionsTest extends BaseTest {

    @Test
    public void testTestProcedure() {
        TestProcedure oBridge = ProceduresAndFunctions.testProcedure("OBridge", ds);
        Assert.assertEquals("Hello OBridge", oBridge.getOut());
    }

    @Test
    public void testSimpleFunction() {
        Execfunction execfunctionTest = ProceduresAndFunctions.execfunction(BigDecimal.ONE, "Hello", ds);
        Assert.assertEquals("IN-Param Nr: 1", execfunctionTest.getFunctionReturn());
        Assert.assertEquals(new BigDecimal(2), execfunctionTest.getPnumber());
        Assert.assertEquals("Out:  In: Hello", execfunctionTest.getPouttext());
    }


}