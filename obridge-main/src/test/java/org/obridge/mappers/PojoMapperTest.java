package org.obridge.mappers;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.obridge.BaseTest;
import org.obridge.dao.ProcedureDao;
import org.obridge.dao.TypeDao;
import org.obridge.model.data.Procedure;
import org.obridge.model.generator.Pojo;
import org.obridge.model.generator.PojoField;
import org.obridge.util.MustacheRunner;

import java.util.List;

public class PojoMapperTest extends BaseTest {

    private static final String SAMPLE_TYPE_ONE = "SAMPLE_TYPE_ONE";
    private TypeDao typeDao;
    private ProcedureDao procedureDao;

    @Before
    public void init2() {
        typeDao = new TypeDao(ds);
        procedureDao = new ProcedureDao(ds);
    }

    @Test
    public void testToPojo() throws Exception {
        Pojo pojo = PojoMapper.typeToPojo(SAMPLE_TYPE_ONE, typeDao.getTypeAttributes(SAMPLE_TYPE_ONE));
        Assert.assertEquals("SampleTypeOne", pojo.getClassName());
        Assert.assertEquals(8, pojo.getFields().size());
        for (PojoField f : pojo.getFields()) {
            Assert.assertFalse(f.isReadonly());
        }
        String pomu = MustacheRunner.build("pojo.mustache", pojo);
    }

    @Test
    public void testToPojo2() throws Exception {

        List<Procedure> simple_procedures = procedureDao.getAllProcedures("SIMPLE_PROCEDURES");

        Procedure ppp = null;

        for (Procedure pp : simple_procedures) {
            if (pp.getProcedureName().equals("OVERLOAD") && pp.getOverload().equals("2")) {
                ppp = pp;
            }
        }

        Pojo pojo = PojoMapper.procedureToPojo(ppp);
        Assert.assertEquals("SimpleProceduresOverload2", pojo.getClassName());
        Assert.assertEquals(1, pojo.getFields().size());

    }

    @Test
    public void testToPojo3() throws Exception {
        List<Procedure> simple_procedures = procedureDao.getAllProcedures("SIMPLE_PROCEDURES");

        Procedure ppp = null;

        for (Procedure pp : simple_procedures) {
            if (pp.getProcedureName().equals("SIMPLE_FUNC") && pp.getOverload().equals("")) {
                ppp = pp;
            }
        }

        Pojo pojo = PojoMapper.procedureToPojo(ppp);
        Assert.assertEquals("SimpleProceduresSimpleFunc", pojo.getClassName());
        Assert.assertEquals(4, pojo.getFields().size());

        String s = MustacheRunner.build("pojo.mustache", pojo);
    }
}