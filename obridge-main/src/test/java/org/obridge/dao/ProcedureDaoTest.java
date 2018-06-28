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
        List<Procedure> allProcedures = procedureDao.getAllProcedure("", "OBRIDGE");
        Collection<String> procedureNames = FuncUtils.pluck("storedProcedureClassName", String.class, allProcedures);
        Assert.assertTrue(procedureNames.contains("SimpleProceduresA"));
        Assert.assertTrue(procedureNames.contains("SimpleProceduresOverload1"));
        Assert.assertTrue(procedureNames.contains("SimpleProceduresOverload2"));
    }

    @Test
    public void testGetProcedureArguments() {
        List<OraclePackage> allPackages = procedureDao.getAllPackages(null, "OBRIDGE");
        Assert.assertTrue(allPackages.size() > 0);

        for (OraclePackage p : allPackages) {
            MustacheRunner.build("package.mustache", p);
        }

    }

    @Test
    public void testGetAllSimpleProcedureAndFunction() {
        List<Procedure> procs = procedureDao.getAllSimpleFunctionAndProcedure("OBRIDGE");
        System.out.println(procs);
    }

    @Test
    public void testAllPckNoFilter() {
        List<OraclePackage> allPackages = procedureDao.getAllPackages("ABCDE", "OBRIDGE");
        Assert.assertTrue(allPackages.size() == 1);

    }
}
