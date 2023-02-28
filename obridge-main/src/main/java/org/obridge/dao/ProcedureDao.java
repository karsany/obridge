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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.obridge.context.OBridgeConfiguration.DbObject;
import org.obridge.model.data.OraclePackage;
import org.obridge.model.data.Procedure;
import org.obridge.model.data.ProcedureArgument;
import org.obridge.util.ResourceUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: fkarsany Date: 2013.11.18.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ProcedureDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Procedure> getAllSimpleFunctionAndProcedure(List<DbObject> objs) {

        String qry = ResourceUtils.load("queries/GET_ALL_PROCEDURES_OUTSIDE_PACKAGE.sql");

        if (objs != null && objs.size() > 0) {
            String s = objs.stream().map(DbObject::toSQL).collect(Collectors.joining(" UNION ALL "));
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
                .argumentList(
                        getProcedureArguments("",
                                resultSet.getString("object_name"),
                                resultSet.getString("overload"),
                                resultSet.getString("owner")))
                .build());

    }

    public List<Procedure> getAllProcedure(String owner, String packageName) {
        log.trace("packageName: {}, owner: {}", packageName, owner);
        return jdbcTemplate.query(ResourceUtils.load("queries/GET_ALL_PROCEDURES_IN_PACKAGE.sql"), (resultSet, i) -> new Procedure.Builder()
                .owner(resultSet.getString("owner"))
                .objectName(resultSet.getString("object_name"))
                .procedureName(resultSet.getString("procedure_name"))
                .overload(resultSet.getString("overload") == null ? "" : resultSet.getString("overload"))
                .methodType(resultSet.getInt("proc_or_func") == 0 ? "PROCEDURE" : "FUNCTION")
                .argumentList(getProcedureArguments(
                        resultSet.getString("object_name"),
                        resultSet.getString("procedure_name"),
                        resultSet.getString("overload"), owner))
                .build(), owner, packageName);

    }

    public List<ProcedureArgument> getProcedureArguments(String packageName, String procedureName, String overLoadNo, String owner) {
        String query = ResourceUtils.load("queries/GET_PROCEDURE_ARGUMENTS.sql");

        log.trace(query);

        return jdbcTemplate.query(query,
                (resultSet, i) -> new ProcedureArgument(resultSet.getString("argument_name"),
                        resultSet.getString("data_type"),
                        resultSet.getString("type_name"),
                        resultSet.getString("in_out").contains("IN"),
                        resultSet.getString("in_out").contains("OUT"),
                        resultSet.getString("orig_type_name")),
                owner,
                owner,
                packageName,
                procedureName,
                overLoadNo);

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
            String s = objs.stream().map(DbObject::toSQL).collect(Collectors.joining(" UNION ALL "));
            qry += " AND (owner, object_name) IN (" + s + ")";
        } else {
            qry += " and owner = user";
        }

        log.trace(qry);

        List<OraclePackage> realPackages = jdbcTemplate.query(qry, (resultSet, i) -> {
            OraclePackage p = new OraclePackage();
            p.setOwner(resultSet.getString("owner"));
            p.setName(resultSet.getString("object_name"));

            List<Procedure> procedures = getAllProcedure(
                    resultSet.getString("owner"),
                    resultSet.getString("object_name"));

            log.trace("getAllProcedure.procedures: {}", procedures);
            p.setProcedureList(procedures);
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
