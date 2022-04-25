package org.obridge.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.obridge.BaseTest;
import org.obridge.context.DbObject;
import org.obridge.model.data.OraclePackage;
import org.obridge.model.data.Procedure;
import org.obridge.model.data.ProcedureArgument;
import org.obridge.util.MustacheRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProcedureDaoTest extends BaseTest {

    private ProcedureDao procedureDao;

    @Before
    public void init2() {
        procedureDao = new ProcedureDao(ds);
    }

    @Test
    public void testGetAllProcedures() {
        List<Procedure> allProcedures = procedureDao.getAllProcedure(null);
        List<String>    procs         = allProcedures
                .stream()
                .map(procedure -> procedure.getStoredProcedureClassName())
                .collect(Collectors.toList());

        System.out.println(procs);

        Assert.assertTrue(procs.contains("SimpleProceduresA"));
        Assert.assertTrue(procs.contains("SimpleProceduresOverload1"));
        Assert.assertTrue(procs.contains("SimpleProceduresOverload2"));
    }

    @Test
    public void testGetProcedureArguments() {
        List<OraclePackage> allPackages = procedureDao.getAllPackages(null);
        Assert.assertTrue(allPackages.size() > 0);

        for (OraclePackage p : allPackages) {
            MustacheRunner.build("package.mustache", p);
        }

    }

    @Test
    public void testGetAllSimpleProcedureAndFunction() {
        List<Procedure> procs = procedureDao.getAllSimpleFunctionAndProcedure(null);
        System.out.println(procs);
    }

    @Test
    public void testAllPckNoFilter() {

        List<DbObject>      dbObjects   = Arrays.asList(new DbObject("OBRIDGE", "ABCDE"));
        List<OraclePackage> allPackages = procedureDao.getAllPackages(dbObjects);
        System.out.println(allPackages);
        Assert.assertTrue(allPackages.size() == 0);

    }

    @Test
    public void getProcedureArguments() {
        final List<ProcedureArgument> procedureArguments = procedureDao.getProcedureArguments("NULLITY_CHECK", "CHECK_OUT_NULL_LIST", null,
                                                                                              "OBRIDGE");
        Assert.assertEquals(1, procedureArguments.size());
        Assert.assertEquals("P_LIST_OBJECT", procedureArguments.get(0).getArgumentName());
    }

    @Test
    public void getAllPackage() {
        List<OraclePackage> obridge = procedureDao.getAllPackages(null);
        Assert.assertTrue(obridge.size() > 0);

        Assert.assertTrue(obridge
                                  .stream()
                                  .map(oraclePackage -> oraclePackage.getName())
                                  .collect(Collectors.toList())
                                  .contains("PROCEDURES_AND_FUNCTIONS"));

    }
}
