package org.obridge.test.database.packages;

import org.obridge.test.database.context.TestPackageAllTypes;
import org.obridge.test.database.context.TestPackageGetSysdate1;
import org.obridge.test.database.context.TestPackageGetSysdate2;
import org.obridge.test.database.context.TestPackageHelloWorld;
import org.obridge.test.database.converters.SampleTypeOneConverter;
import org.obridge.test.database.objects.SampleTypeOne;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;


public class TestPackage {

    public static void allTypes(TestPackageAllTypes ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall("{ CALL TEST_PACKAGE.ALL_TYPES( N => ? , BI => ? , PI => ? , VCH => ? , NVCH => ? , CH => ? , NCH => ? , D => ? , TS => ? , CL => ? , B => ? , TBL => ? , O => ? ) }");
        ocs.setBigDecimal(1, ctx.getN()); // N
        ocs.registerOutParameter(1, Types.NUMERIC); // N
        System.out.println(ctx.getBi());
        ocs.setNull(2, Types.INTEGER); // BI
        ocs.registerOutParameter(2, Types.INTEGER); // BI
        ocs.setInt(3, ctx.getPi()); // PI
        ocs.registerOutParameter(3, Types.INTEGER); // PI
        ocs.setString(4, ctx.getVch()); // VCH
        ocs.registerOutParameter(4, Types.VARCHAR); // VCH
        ocs.setString(5, ctx.getNvch()); // NVCH
        ocs.registerOutParameter(5, Types.NVARCHAR); // NVCH
        ocs.setString(6, ctx.getCh()); // CH
        ocs.registerOutParameter(6, Types.CHAR); // CH
        ocs.setString(7, ctx.getNch()); // NCH
        ocs.registerOutParameter(7, Types.NCHAR); // NCH
        ocs.setDate(8, ctx.getD()); // D
        ocs.registerOutParameter(8, Types.DATE); // D
        ocs.setTimestamp(9, ctx.getTs()); // TS
        ocs.registerOutParameter(9, Types.TIMESTAMP); // TS
        ocs.setString(10, ctx.getCl()); // CL
        ocs.registerOutParameter(10, Types.CLOB); // CL
        ocs.setBoolean(11, ctx.getB()); // B
        ocs.registerOutParameter(11, Types.BOOLEAN); // B
        ocs.setObject(12, SampleTypeOneConverter.getListArray(ctx.getTbl(), connection)); // TBL
        ocs.registerOutParameter(12, Types.ARRAY, "SAMPLE_TYPE_ONE"); // TBL
        ocs.setObject(13, SampleTypeOneConverter.getStruct(ctx.getO(), connection)); // O
        ocs.registerOutParameter(13, Types.STRUCT, "SAMPLE_TYPE_ONE"); // O
        ocs.execute();
        ctx.setN(ocs.getBigDecimal(1)); // N
        ctx.setBi(ocs.getInt(2)); // BI
        ctx.setPi(ocs.getInt(3)); // PI
        ctx.setVch(ocs.getString(4)); // VCH
        ctx.setNvch(ocs.getString(5)); // NVCH
        ctx.setCh(ocs.getString(6)); // CH
        ctx.setNch(ocs.getString(7)); // NCH
        ctx.setD(ocs.getDate(8)); // D
        ctx.setTs(ocs.getTimestamp(9)); // TS
        ctx.setCl(ocs.getString(10)); // CL
        ctx.setB(ocs.getBoolean(11)); // B
        ctx.setTbl(SampleTypeOneConverter.getObjectList((Array) ocs.getObject(12))); // TBL
        ctx.setO(SampleTypeOneConverter.getObject((Struct) ocs.getObject(13))); // O
        ocs.close();
    }

    public static TestPackageAllTypes allTypes(BigDecimal n, Integer bi, Integer pi, String vch, String nvch, String ch, String nch, Date d, Timestamp ts, String cl, Boolean b, List<SampleTypeOne> tbl, SampleTypeOne o, Connection connection) throws SQLException {
        TestPackageAllTypes ctx = new TestPackageAllTypes();
        ctx.setN(n);
        ctx.setBi(bi);
        ctx.setPi(pi);
        ctx.setVch(vch);
        ctx.setNvch(nvch);
        ctx.setCh(ch);
        ctx.setNch(nch);
        ctx.setD(d);
        ctx.setTs(ts);
        ctx.setCl(cl);
        ctx.setB(b);
        ctx.setTbl(tbl);
        ctx.setO(o);

        allTypes(ctx, connection);

        return ctx;
    }

    public static TestPackageAllTypes allTypes(BigDecimal n, Integer bi, Integer pi, String vch, String nvch, String ch, String nch, Date d, Timestamp ts, String cl, Boolean b, List<SampleTypeOne> tbl, SampleTypeOne o, DataSource dataSource) {
        Connection conn = null;
        TestPackageAllTypes ret = null;

        try {
            conn = dataSource.getConnection();
            ret = allTypes(n, bi, pi, vch, nvch, ch, nch, d, ts, cl, b, tbl, o, conn);

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
