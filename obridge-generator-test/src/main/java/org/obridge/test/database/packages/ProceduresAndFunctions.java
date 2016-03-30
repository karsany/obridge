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


    public static void execfunction(Execfunction ctx, Connection connection) {
        try {
            final CallableStatement ocs = connection.prepareCall(                "" +
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
            try {
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
            } finally  {
                ocs.close();
            }
        } catch (SQLException e) {
            throw new StoredProcedureCallException(e);
        }
    }

    public static Execfunction execfunction(BigDecimal pnumber, String pintext,  Connection connection) {
        final Execfunction ctx = new Execfunction();
        ctx.setPnumber(pnumber);
        ctx.setPintext(pintext);
        execfunction(ctx, connection);
        return ctx;
    }

    public static Execfunction execfunction(BigDecimal pnumber, String pintext,  DataSource dataSource) {
        try {
            final Connection conn = dataSource.getConnection();
            try {
                return execfunction(pnumber, pintext,  conn);
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            throw new StoredProcedureCallException(e);
        }
    }


    public static void execfunction(DataSource dataSource, Execfunction ctx) {
        try {
            final Connection conn = dataSource.getConnection();
            try {
                execfunction(ctx, conn);
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            throw new StoredProcedureCallException(e);
        }
    }



    public static void testProcedure(TestProcedure ctx, Connection connection) {
        try {
            final CallableStatement ocs = connection.prepareCall(                "" +
                                          "DECLARE " +
                                          "BEGIN " +
                                          "  \"TEST_PROCEDURE\"( " +
                                          "    \"P_NAME\" => :P_NAME" +
                                          "   ,\"P_OUT\" => :P_OUT" +
                                          "   );" +
                                          "END;" +
                                          "");
            try {
                // Set P_NAME from context name
                if (ctx.getName() != null) {
                    ocs.setString(1, ctx.getName());
                } else {
                    ocs.setNull(1, Types.VARCHAR);
                }
                ocs.registerOutParameter(2, Types.VARCHAR); // P_OUT
                ocs.execute();
                ctx.setOut(ocs.getString(2)); // P_OUT
            } finally  {
                ocs.close();
            }
        } catch (SQLException e) {
            throw new StoredProcedureCallException(e);
        }
    }

    public static TestProcedure testProcedure(String name,  Connection connection) {
        final TestProcedure ctx = new TestProcedure();
        ctx.setName(name);
        testProcedure(ctx, connection);
        return ctx;
    }

    public static TestProcedure testProcedure(String name,  DataSource dataSource) {
        try {
            final Connection conn = dataSource.getConnection();
            try {
                return testProcedure(name,  conn);
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            throw new StoredProcedureCallException(e);
        }
    }


    public static void testProcedure(DataSource dataSource, TestProcedure ctx) {
        try {
            final Connection conn = dataSource.getConnection();
            try {
                testProcedure(ctx, conn);
            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            throw new StoredProcedureCallException(e);
        }
    }



}
