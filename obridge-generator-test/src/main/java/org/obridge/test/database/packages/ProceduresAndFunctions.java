package org.obridge.test.database.packages;

import org.obridge.test.database.context.*;
import org.obridge.test.database.converters.*;
import org.obridge.test.database.objects.*;

import javax.annotation.Generated;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Arrays;

@Generated("org.obridge.generators.PackageObjectGenerator")
public final class ProceduresAndFunctions {

    private ProceduresAndFunctions() {
    }


    public static void execfunction(Execfunction ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" +
                                "DECLARE " +
                                "BEGIN " +
                                "  :result := " +
                                "  \"EXECFUNCTION\"( " +
                                "    \"PNUMBER\" => :PNUMBER" +
                                "   ,\"PINTEXT\" => :PINTEXT" +
                                "   ,\"POUTTEXT\" => :POUTTEXT" +
                                "   );" +
                                "END;" +
                                "");
        ocs.registerOutParameter(1, Types.VARCHAR); // null
        // Set PNUMBER from context pnumber
        if (ctx.getPnumber() != null) {
            ocs.setBigDecimal(2, ctx.getPnumber());
        } else {
            ocs.setNull(2, Types.NUMERIC);
        }
        ocs.registerOutParameter(2, Types.NUMERIC); // PNUMBER
        // Set PINTEXT from context pintext
        if (ctx.getPintext() != null) {
            ocs.setString(3, ctx.getPintext());
        } else {
            ocs.setNull(3, Types.VARCHAR);
        }
        ocs.registerOutParameter(4, Types.VARCHAR); // POUTTEXT
        ocs.execute();
        ctx.setFunctionReturn(ocs.getString(1)); // null
        ctx.setPnumber(ocs.getBigDecimal(2)); // PNUMBER
        ctx.setPouttext(ocs.getString(4)); // POUTTEXT
        ocs.close();
    }

    public static Execfunction execfunction(BigDecimal pnumber, String pintext,  Connection connection) throws SQLException {
        Execfunction ctx = new Execfunction();
        ctx.setPnumber(pnumber);
        ctx.setPintext(pintext);
        execfunction(ctx, connection);
        return ctx;
    }

    public static Execfunction execfunction(BigDecimal pnumber, String pintext,  DataSource dataSource) {
        Connection conn = null;
        Execfunction ret = null;
        try {
            conn = dataSource.getConnection();
            return execfunction(pnumber, pintext,  conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void testProcedure(TestProcedure ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" +
                                "DECLARE " +
                                "BEGIN " +
                                "  \"TEST_PROCEDURE\"( " +
                                "    \"P_NAME\" => :P_NAME" +
                                "   ,\"P_OUT\" => :P_OUT" +
                                "   );" +
                                "END;" +
                                "");
        // Set P_NAME from context name
        if (ctx.getName() != null) {
            ocs.setString(1, ctx.getName());
        } else {
            ocs.setNull(1, Types.VARCHAR);
        }
        ocs.registerOutParameter(2, Types.VARCHAR); // P_OUT
        ocs.execute();
        ctx.setOut(ocs.getString(2)); // P_OUT
        ocs.close();
    }

    public static TestProcedure testProcedure(String name,  Connection connection) throws SQLException {
        TestProcedure ctx = new TestProcedure();
        ctx.setName(name);
        testProcedure(ctx, connection);
        return ctx;
    }

    public static TestProcedure testProcedure(String name,  DataSource dataSource) {
        Connection conn = null;
        TestProcedure ret = null;
        try {
            conn = dataSource.getConnection();
            return testProcedure(name,  conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
