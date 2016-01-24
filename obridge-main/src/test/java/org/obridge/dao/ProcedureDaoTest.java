package org.obridge.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.obridge.BaseTest;
import org.obridge.model.data.OraclePackage;
import org.obridge.model.data.Procedure;
import org.obridge.util.FuncUtils;
import org.obridge.util.MustacheRunner;

import java.util.Collection;
import java.util.List;

public class ProcedureDaoTest extends BaseTest {

    private ProcedureDao procedureDao;

    @Before
    public void init2() {
        procedureDao = new ProcedureDao(ds);
    }

    @Test
    public void testGetAllProcedures() {
        List<Procedure> allProcedures = procedureDao.getAllProcedure();
        Collection<String> procedureNames = FuncUtils.pluck("storedProcedureClassName", String.class, allProcedures);
        Assert.assertTrue(procedureNames.contains("SimpleProceduresA"));
        Assert.assertTrue(procedureNames.contains("SimpleProceduresOverload1"));
        Assert.assertTrue(procedureNames.contains("SimpleProceduresOverload2"));
    }

    @Test
    public void testGetProcedureArguments() {
        List<OraclePackage> allPackages = procedureDao.getAllPackages();

        for (OraclePackage p : allPackages) {
            MustacheRunner.build("package.mustache", p);
        }

    }

    @Test
    public void testGetAllSimpleProcedureAndFunction() {
        List<Procedure> procs = procedureDao.getAllSimpleFunctionAndProcedure();
        System.out.println(procs);
    }
}
