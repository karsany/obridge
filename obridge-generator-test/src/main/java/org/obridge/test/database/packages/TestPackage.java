package org.obridge.test.database.packages;

import java.sql.*;
import javax.sql.DataSource;
import org.obridge.test.database.context.*;
import org.obridge.test.database.converters.*;
import org.obridge.test.database.objects.*;
import java.util.List;
import java.math.BigDecimal;


public class TestPackage {

    public static void getSysdate1(TestPackageGetSysdate1 ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall("{ CALL ? := TEST_PACKAGE.GET_SYSDATE(  ) }");
        ocs.registerOutParameter(1, Types.DATE); // null
        ocs.execute();
        ctx.setFunctionReturn(ocs.getDate(1)); // null
        ocs.close();
    }

    public static TestPackageGetSysdate1 getSysdate1( Connection connection) throws SQLException {
        TestPackageGetSysdate1 ctx = new TestPackageGetSysdate1();

        getSysdate1(ctx, connection);

        return ctx;
    }

    public static TestPackageGetSysdate1 getSysdate1( DataSource dataSource) {
        Connection conn = null;
        TestPackageGetSysdate1 ret = null;

        try {
            conn = dataSource.getConnection();
            ret = getSysdate1( conn);

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
            return ret;
        }
    }

    public static void getSysdate2(TestPackageGetSysdate2 ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall("{ CALL TEST_PACKAGE.GET_SYSDATE( P_SYSDATE => ? ) }");
        ocs.registerOutParameter(1, Types.DATE); // P_SYSDATE
        ocs.execute();
        ctx.setSysdate(ocs.getDate(1)); // P_SYSDATE
        ocs.close();
    }

    public static TestPackageGetSysdate2 getSysdate2( Connection connection) throws SQLException {
        TestPackageGetSysdate2 ctx = new TestPackageGetSysdate2();

        getSysdate2(ctx, connection);

        return ctx;
    }

    public static TestPackageGetSysdate2 getSysdate2( DataSource dataSource) {
        Connection conn = null;
        TestPackageGetSysdate2 ret = null;

        try {
            conn = dataSource.getConnection();
            ret = getSysdate2( conn);

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
            return ret;
        }
    }

    public static void helloWorld(TestPackageHelloWorld ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall("{ CALL TEST_PACKAGE.HELLO_WORLD( P_NAME => ? , P_OUT => ? ) }");
        ocs.setString(1, ctx.getName()); // P_NAME
        ocs.registerOutParameter(2, Types.VARCHAR); // P_OUT
        ocs.execute();
        ctx.setOut(ocs.getString(2)); // P_OUT
        ocs.close();
    }

    public static TestPackageHelloWorld helloWorld(String name,  Connection connection) throws SQLException {
        TestPackageHelloWorld ctx = new TestPackageHelloWorld();
        ctx.setName(name);

        helloWorld(ctx, connection);

        return ctx;
    }

    public static TestPackageHelloWorld helloWorld(String name,  DataSource dataSource) {
        Connection conn = null;
        TestPackageHelloWorld ret = null;

        try {
            conn = dataSource.getConnection();
            ret = helloWorld(name,  conn);

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
            return ret;
        }
    }


}
