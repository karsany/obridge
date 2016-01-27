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
