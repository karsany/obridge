package org.obridge.mappers.builders;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.model.data.ProcedureArgument;

public class ParameterGetSetRegisterBuilderTest {

    @Test
    public void testSetParameter() throws Exception {
        ParameterGetSetRegisterBuilder parameterGetSetRegisterBuilder = new ParameterGetSetRegisterBuilder(new ProcedureArgument("P_HELLO", "VARCHAR2", null, null, true, false, 1, "VARCHAR2"));
        String s = parameterGetSetRegisterBuilder.setParameter(1);
        Assert.assertTrue(s.contains("Types.VARCHAR"));
    }
}