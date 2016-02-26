package org.obridge.dao;

import org.obridge.model.data.OraclePackage;
import org.obridge.model.data.Procedure;
import org.obridge.model.data.ProcedureArgument;
import org.obridge.util.jdbc.JdbcTemplate;
import org.obridge.util.jdbc.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User: fkarsany Date: 2013.11.18.
 */
public class ProcedureDao {

    private static final String GET_ALL_PROCEDURE = "Select object_name\n"
            + "      ,procedure_name\n"
            + "      ,overload\n"
            + "      ,(Select Count(*)\n"
            + "         From user_arguments a\n"
            + "        Where a.object_name = t.procedure_name\n"
            + "          And a.package_name = t.object_name\n"
            + "          And nvl(a.overload,'##NVL##') = nvl(t.overload,'##NVL##')\n"
            + "          And a.argument_name Is Null\n"
            + "          And a.data_level = 0"
            + "          And a.data_type is not null) proc_or_func\n"
            + "  From user_procedures t\n"
            + " Where procedure_name Is Not Null\n"
            + "   And object_type = 'PACKAGE'"
            + "   And object_name like ? "
            + "   And procedure_name like ? "
            + " and not ((object_name, procedure_name, nvl(overload, -1)) In\n"
            + "       (Select package_name,\n"
            + "               object_name,\n"
            + "               nvl(overload, -1)\n"
            + "          From user_arguments\n"
            + "         Where data_type in ( 'PL/SQL RECORD', 'PL/SQL TABLE' )\n"
            + "    Or (data_type = 'REF CURSOR' And in_out Like '%IN%')\n"
            + ") or procedure_name = 'ASSERT')\n";


    private static final String GET_ALL_SIMPLE_FUNCTION_AND_PROCEDURE = "Select object_name\n"
            + "      ,procedure_name\n"
            + "      ,overload\n"
            + "      ,(Select Count(*)\n"
            + "         From user_arguments a\n"
            + "        Where a.object_name = t.object_name\n"
            + "          And a.package_name Is Null\n"
            + "          And nvl(a.overload,'##NVL##') = nvl(t.overload,'##NVL##')\n"
            + "          And a.argument_name Is Null\n"
            + "          And a.data_level = 0"
            + "          And a.data_type is not null) proc_or_func\n"
            + "  From user_procedures t\n"
            + " Where procedure_name Is Null\n"
            + "   And object_type in ( 'PROCEDURE', 'FUNCTION' )"
            + " and not ((object_name, procedure_name, nvl(overload, -1)) In\n"
            + "       (Select object_name,\n"
            + "               package_name,\n"
            + "               nvl(overload, -1)\n"
            + "          From user_arguments\n"
            + "         Where data_type in ( 'PL/SQL RECORD', 'PL/SQL TABLE' )\n"
            + "    Or (data_type = 'REF CURSOR' And in_out Like '%IN%')\n"
            + ") or object_name = 'ASSERT')\n";

    private static final String GET_PROCEDURE_ARGUMENTS = "  select argument_name," +
            "data_type," +
            "nvl( (select max(elem_type_name) from user_coll_types w where w.TYPE_NAME = p.type_name) , p.type_name) type_name," +
            "defaulted," +
            "in_out," +
            "rownum sequen, p.type_name orig_type_name " +
            "from (Select argument_name, data_type, type_name, defaulted, in_out\n"
            + "        From user_arguments t\n"
            + "       Where nvl(t.package_name, '###') = nvl((?), '###')\n"
            + "         And t.object_name = (?)\n"
            + "         And nvl(t.overload, '###') = nvl(?, '###')\n"
            + "         And t.data_level = 0\n"
            + "         And not(pls_type is null and argument_name is null and data_type is null)"
            + "       Order By t.sequence) p\n";


    private JdbcTemplate jdbcTemplate;

    public ProcedureDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Procedure> getAllProcedure() {
        List<Procedure> allProcedures = getAllProcedure("", "");
        allProcedures.addAll(getAllSimpleFunctionAndProcedure());

        return allProcedures;
    }

    public List<Procedure> getAllSimpleFunctionAndProcedure() {
        return jdbcTemplate.query(
                GET_ALL_SIMPLE_FUNCTION_AND_PROCEDURE,
                new RowMapper<Procedure>() {
                    @Override
                    public Procedure mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Procedure(
                                "",
                                resultSet.getString("object_name"),
                                resultSet.getString("overload") == null ? "" : resultSet.getString("overload"),
                                resultSet.getInt("proc_or_func") == 0 ? "PROCEDURE" : "FUNCTION",
                                getProcedureArguments("",
                                        resultSet.getString("object_name"),
                                        resultSet.getString("overload"))
                        );
                    }
                }
        );

    }

    public List<Procedure> getAllProcedure(String packageName, String procedureName) {
        String packageNameFilter = "";
        String procedureNameFilter = "";

        if (packageName == null || packageName.isEmpty() || "".equals(packageName)) {
            packageNameFilter = "%";
        } else {
            packageNameFilter = packageName;
        }

        if (procedureName == null || procedureName.isEmpty() || "".equals(procedureName)) {
            procedureNameFilter = "%";
        } else {
            procedureNameFilter = procedureName;
        }


        return jdbcTemplate.query(
                GET_ALL_PROCEDURE,
                new RowMapper<Procedure>() {
                    @Override
                    public Procedure mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new Procedure(
                                resultSet.getString("object_name"),
                                resultSet.getString("procedure_name"),
                                resultSet.getString("overload") == null ? "" : resultSet.getString("overload"),
                                resultSet.getInt("proc_or_func") == 0 ? "PROCEDURE" : "FUNCTION",
                                getProcedureArguments(resultSet.getString("object_name"),
                                        resultSet.getString("procedure_name"),
                                        resultSet.getString("overload"))
                        );
                    }
                }, packageNameFilter, procedureNameFilter
        );

    }

    public List<ProcedureArgument> getProcedureArguments(String packageName, String procedureName, String overLoadNo) {
        return jdbcTemplate.query(
                GET_PROCEDURE_ARGUMENTS,
                new Object[]{packageName, procedureName, overLoadNo}, new RowMapper<ProcedureArgument>() {
                    @Override
                    public ProcedureArgument mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new ProcedureArgument(
                                resultSet.getString("argument_name"),
                                resultSet.getString("data_type"),
                                resultSet.getString("type_name"),
                                resultSet.getString("defaulted"),
                                resultSet.getString("in_out").contains("IN"),
                                resultSet.getString("in_out").contains("OUT"),
                                resultSet.getInt("sequen"),
                                resultSet.getString("orig_type_name")
                        );
                    }
                }
        );

    }

    public List<OraclePackage> getAllPackages() {
        List<OraclePackage> allPackage = getAllRealOraclePackage();
        allPackage.add(getAllStandaloneProcedureAndFunction());
        return allPackage;
    }

    private OraclePackage getAllStandaloneProcedureAndFunction() {
        OraclePackage oraclePackage = new OraclePackage();
        oraclePackage.setName("PROCEDURES_AND_FUNCTIONS");
        oraclePackage.setProcedureList(getAllSimpleFunctionAndProcedure());
        return oraclePackage;
    }

    private List<OraclePackage> getAllRealOraclePackage() {
        return jdbcTemplate.query("select object_name from user_objects where object_type = 'PACKAGE'", new RowMapper<OraclePackage>() {
            @Override
            public OraclePackage mapRow(ResultSet resultSet, int i) throws SQLException {
                OraclePackage p = new OraclePackage();
                p.setName(resultSet.getString("object_name"));
                p.setProcedureList(getAllProcedure(resultSet.getString("object_name"), ""));
                return p;
            }
        });
    }

}
