/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Ferenc Karsany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package org.obridge.dao;

import org.obridge.context.DbObject;
import org.obridge.model.data.OraclePackage;
import org.obridge.model.data.Procedure;
import org.obridge.model.data.ProcedureArgument;
import org.obridge.util.ResourceUtils;
import org.obridge.util.jdbc.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: fkarsany Date: 2013.11.18.
 */
public class ProcedureDao {

    private static final String GET_PROCEDURE_ARGUMENTS = "  select argument_name," + "data_type," + "nvl( (select " + "max" +
            "(elem_type_name) " + "from all_coll_types w where owner = ? " + "and w" + ".TYPE_NAME = p.type_name) , p.type_name || case " + "when p.type_subname is not null " + "then '_' || p" + ".type_subname end)" + " type_name," + "defaulted," + "in_out," + "rownum " + "sequen, p.type_name orig_type_name " + "from (Select " + "argument_name, data_type, type_name, type_subname, defaulted, " + "in_out\n" + "        From all_arguments t\n" + " " + "      Where owner = ? and nvl(t.package_name, '###') = nvl((?), '###')" + "\n" + "         And t.object_name = (?)\n" + "        " + " And nvl(t.overload, '###') = nvl(?, '###')\n" + "         And t" + ".data_level = 0\n" + "         And not" + "(pls_type is null and " + "argument_name is null and data_type is null)" + "     " + "  Order By t.sequence) p\n";

    private JdbcTemplate jdbcTemplate;

    public ProcedureDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Procedure> getAllSimpleFunctionAndProcedure(List<DbObject> objs) {

        String qry = ResourceUtils.load("queries/GET_ALL_PROCEDURES_OUTSIDE_PACKAGE.sql");

        if (objs != null && objs.size() > 0) {
            String s = objs.stream().map(dbObject -> dbObject.toSQL()).collect(Collectors.joining(" UNION ALL"));
            qry += " AND (owner, object_name) IN (" + s + ")";
        } else {
            qry += " and owner = user";
        }

        return jdbcTemplate.query(qry, (resultSet, i) -> new Procedure.Builder()
                .objectName("")
                .owner(resultSet.getString("owner"))
                .procedureName(resultSet.getString("object_name"))
                .overload(resultSet.getString("overload") == null ? "" : resultSet.getString("overload"))
                .methodType(resultSet.getInt("proc_or_func") == 0 ? "PROCEDURE" : "FUNCTION")
                .argumentList(getProcedureArguments("", resultSet.getString("object_name"), resultSet.getString("overload"),
                                                    resultSet.getString("owner")))
                .build());

    }

    public List<Procedure> getAllProcedure(String owner, String packageName) {

        return jdbcTemplate.query(ResourceUtils.load("queries/GET_ALL_PROCEDURES_IN_PACKAGE.sql"), (resultSet, i) -> new Procedure.Builder()
                .owner(resultSet.getString("owner"))
                .objectName(resultSet.getString("object_name"))
                .procedureName(resultSet.getString("procedure_name"))
                .overload(resultSet.getString("overload") == null ? "" : resultSet.getString("overload"))
                .methodType(resultSet.getInt("proc_or_func") == 0 ? "PROCEDURE" : "FUNCTION")
                .argumentList(getProcedureArguments(resultSet.getString("object_name"), resultSet.getString("procedure_name"),
                                                    resultSet.getString("overload"), owner))
                .build(), owner, packageName);

    }

    public List<ProcedureArgument> getProcedureArguments(String packageName, String procedureName, String overLoadNo, String owner) {
        return jdbcTemplate.query(GET_PROCEDURE_ARGUMENTS, new Object[]{owner, owner, packageName, procedureName, overLoadNo},
                                  (resultSet, i) -> new ProcedureArgument(resultSet.getString("argument_name"),
                                                                          resultSet.getString("data_type"),
                                                                          resultSet.getString("type_name"),
                                                                          resultSet.getString("in_out").contains("IN"),
                                                                          resultSet.getString("in_out").contains("OUT"),
                                                                          resultSet.getString("orig_type_name")));

    }

    private OraclePackage getAllStandaloneProcedureAndFunction(List<DbObject> objects) {
        OraclePackage oraclePackage = new OraclePackage();
        oraclePackage.setName("PROCEDURES_AND_FUNCTIONS");
        oraclePackage.setProcedureList(getAllSimpleFunctionAndProcedure(objects));
        return oraclePackage;
    }

    public List<OraclePackage> getAllPackages(List<DbObject> objs) {

        String qry = ResourceUtils.load("queries/GET_ALL_PACKAGES.sql");

        if (objs != null && objs.size() > 0) {
            String s = objs.stream().map(dbObject -> dbObject.toSQL()).collect(Collectors.joining(" UNION ALL"));
            qry += " AND (owner, object_name) IN (" + s + ")";
        } else {
            qry += " and owner = user";
        }

        System.out.println(qry);

        List<OraclePackage> realPackages = jdbcTemplate.query(qry, (resultSet, i) -> {
            OraclePackage p = new OraclePackage();
            p.setOwner(resultSet.getString("owner"));
            p.setName(resultSet.getString("object_name"));
            p.setProcedureList(getAllProcedure(resultSet.getString("owner"), resultSet.getString("object_name")));
            return p;
        });

        OraclePackage aaa = getAllStandaloneProcedureAndFunction(objs);
        if (aaa.getProcedureList().size() > 0) {
            realPackages.add(aaa);
        }

        return realPackages;
    }

    public List<Procedure> getAllProcedure(List<DbObject> dbObjects) {

        List<Procedure> ret = new ArrayList<>();

        List<OraclePackage> allPackages = getAllPackages(dbObjects);
        for (OraclePackage allPackage : allPackages) {
            ret.addAll(allPackage.getProcedureList());
        }

        return ret;
    }
}
