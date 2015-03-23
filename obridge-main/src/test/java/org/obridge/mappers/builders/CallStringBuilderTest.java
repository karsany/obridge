package org.obridge.mappers.builders;

import org.junit.Before;
import org.junit.Test;
import org.obridge.BaseTest;
import org.obridge.dao.ProcedureDao;
import org.obridge.model.data.Procedure;

public class CallStringBuilderTest extends BaseTest {

    private Procedure p;

    @Before
    public void setUp() {
        p = new ProcedureDao(ds).getAllProcedures(null, "SIMPLE_BOOLEAN_RETURN").get(0);
    }

    @Test
    public void testBuild1() {
        String callString = new CallStringBuilder(p).build();
        System.out.println(callString);
        System.out.println(p.getReturnJavaType());
    }
}