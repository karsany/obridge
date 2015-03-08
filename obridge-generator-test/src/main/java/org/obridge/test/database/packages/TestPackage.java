package org.obridge.test.database.packages;

import java.sql.*;
import javax.sql.DataSource;
import org.obridge.test.database.context.*;
import org.obridge.test.database.converters.*;
import org.obridge.test.database.objects.*;
import java.util.List;
import java.math.BigDecimal;


public class TestPackage {

    public static void allTypes(TestPackageAllTypes ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" + 
                "DECLARE " + 
                "  B BOOLEAN := sys.diutil.int_to_bool(:iB); " + 
                "BEGIN " + 
                "  TEST_PACKAGE.ALL_TYPES( " + 
                "    N => :N" + 
                "   ,BI => :BI" + 
                "   ,PI => :PI" + 
                "   ,VCH => :VCH" + 
                "   ,NVCH => :NVCH" + 
                "   ,CH => :CH" + 
                "   ,NCH => :NCH" + 
                "   ,D => :D" + 
                "   ,TS => :TS" + 
                "   ,CL => :CL" + 
                "   ,B => B" + 
                "   ,TBL => :TBL" + 
                "   ,O => :O" + 
                "   );" + 
                "  :oB := sys.diutil.bool_to_int(B);" + 
                "END;" + 
"");
                    // Set B from context b
            if (ctx.getB() != null) {
                ocs.setInt(1, ctx.getB() ? 1 : 0);
            } else {
                ocs.setNull(1, Types.INTEGER);
            }

                    // Set N from context n
            if (ctx.getN() != null) {
                ocs.setBigDecimal(2, ctx.getN());
            } else {
                ocs.setNull(2, Types.NUMERIC);
            }

        ocs.registerOutParameter(2, Types.NUMERIC); // N
                    // Set BI from context bi
            if (ctx.getBi() != null) {
                ocs.setInt(3, ctx.getBi());
            } else {
                ocs.setNull(3, Types.INTEGER);
            }

        ocs.registerOutParameter(3, Types.INTEGER); // BI
                    // Set PI from context pi
            if (ctx.getPi() != null) {
                ocs.setInt(4, ctx.getPi());
            } else {
                ocs.setNull(4, Types.INTEGER);
            }

        ocs.registerOutParameter(4, Types.INTEGER); // PI
                    // Set VCH from context vch
            if (ctx.getVch() != null) {
                ocs.setString(5, ctx.getVch());
            } else {
                ocs.setNull(5, Types.VARCHAR);
            }

        ocs.registerOutParameter(5, Types.VARCHAR); // VCH
                    // Set NVCH from context nvch
            if (ctx.getNvch() != null) {
                ocs.setString(6, ctx.getNvch());
            } else {
                ocs.setNull(6, Types.NVARCHAR);
            }

        ocs.registerOutParameter(6, Types.NVARCHAR); // NVCH
                    // Set CH from context ch
            if (ctx.getCh() != null) {
                ocs.setString(7, ctx.getCh());
            } else {
                ocs.setNull(7, Types.CHAR);
            }

        ocs.registerOutParameter(7, Types.CHAR); // CH
                    // Set NCH from context nch
            if (ctx.getNch() != null) {
                ocs.setString(8, ctx.getNch());
            } else {
                ocs.setNull(8, Types.NCHAR);
            }

        ocs.registerOutParameter(8, Types.NCHAR); // NCH
                    // Set D from context d
            if (ctx.getD() != null) {
                ocs.setDate(9, ctx.getD());
            } else {
                ocs.setNull(9, Types.DATE);
            }

        ocs.registerOutParameter(9, Types.DATE); // D
                    // Set TS from context ts
            if (ctx.getTs() != null) {
                ocs.setTimestamp(10, ctx.getTs());
            } else {
                ocs.setNull(10, Types.TIMESTAMP);
            }

        ocs.registerOutParameter(10, Types.TIMESTAMP); // TS
                    // Set CL from context cl
            if (ctx.getCl() != null) {
                ocs.setString(11, ctx.getCl());
            } else {
                ocs.setNull(11, Types.CLOB);
            }

        ocs.registerOutParameter(11, Types.CLOB); // CL
                    // Set TBL from context tbl
            ocs.setObject(12, SampleTypeOneConverter.getListArray(ctx.getTbl(), connection, "SAMPLE_TYPE_ONE_LIST"));
        ocs.registerOutParameter(12, Types.ARRAY, "SAMPLE_TYPE_ONE_LIST"); // TBL
                    // Set O from context o
            if (ctx.getO() != null) {
                ocs.setObject(13, SampleTypeOneConverter.getStruct(ctx.getO(), connection));
            } else {
                ocs.setNull(13, Types.STRUCT, "SAMPLE_TYPE_ONE");
            }

        ocs.registerOutParameter(13, Types.STRUCT, "SAMPLE_TYPE_ONE"); // O
        ocs.registerOutParameter(14, Types.INTEGER); // B
        ocs.execute();
        ctx.setN(ocs.getBigDecimal(2)); // N
        ctx.setBi(ocs.getInt(3)); // BI
        ctx.setPi(ocs.getInt(4)); // PI
        ctx.setVch(ocs.getString(5)); // VCH
        ctx.setNvch(ocs.getString(6)); // NVCH
        ctx.setCh(ocs.getString(7)); // CH
        ctx.setNch(ocs.getString(8)); // NCH
        ctx.setD(ocs.getDate(9)); // D
        ctx.setTs(ocs.getTimestamp(10)); // TS
        ctx.setCl(ocs.getString(11)); // CL
        ctx.setTbl(SampleTypeOneConverter.getObjectList((Array)ocs.getObject(12))); // TBL
        ctx.setO(SampleTypeOneConverter.getObject((Struct)ocs.getObject(13))); // O
        ctx.setB(null == ocs.getBigDecimal(14) ? null : BigDecimal.ONE.equals(ocs.getBigDecimal(14)) ? true : BigDecimal.ZERO.equals(ocs.getBigDecimal(14)) ? false : null); // B
        ocs.close();
    }

    public static TestPackageAllTypes allTypes(BigDecimal n, Integer bi, Integer pi, String vch, String nvch, String ch, String nch, Date d, Timestamp ts, String cl, Boolean b, List<SampleTypeOne> tbl, SampleTypeOne o,  Connection connection) throws SQLException {
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

    public static TestPackageAllTypes allTypes(BigDecimal n, Integer bi, Integer pi, String vch, String nvch, String ch, String nch, Date d, Timestamp ts, String cl, Boolean b, List<SampleTypeOne> tbl, SampleTypeOne o,  DataSource dataSource) {
        Connection conn = null;
        TestPackageAllTypes ret = null;

        try {
            conn = dataSource.getConnection();
            ret = allTypes(n, bi, pi, vch, nvch, ch, nch, d, ts, cl, b, tbl, o,  conn);
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void booleanTest1(TestPackageBooleanTest1 ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" + 
                "DECLARE " + 
                "  P_BOOL BOOLEAN := sys.diutil.int_to_bool(:iP_BOOL); " + 
                "BEGIN " + 
                "  :result := " + 
                "  TEST_PACKAGE.BOOLEAN_TEST_1( " + 
                "    P_BOOL => P_BOOL" + 
                "   );" + 
                "END;" + 
"");
                    // Set P_BOOL from context bool
            if (ctx.getBool() != null) {
                ocs.setInt(1, ctx.getBool() ? 1 : 0);
            } else {
                ocs.setNull(1, Types.INTEGER);
            }

        ocs.registerOutParameter(2, Types.VARCHAR); // null
        ocs.execute();
        ctx.setFunctionReturn(ocs.getString(2)); // null
        ocs.close();
    }

    public static TestPackageBooleanTest1 booleanTest1(Boolean bool,  Connection connection) throws SQLException {
        TestPackageBooleanTest1 ctx = new TestPackageBooleanTest1();
        ctx.setBool(bool);

        booleanTest1(ctx, connection);

        return ctx;
    }

    public static TestPackageBooleanTest1 booleanTest1(Boolean bool,  DataSource dataSource) {
        Connection conn = null;
        TestPackageBooleanTest1 ret = null;

        try {
            conn = dataSource.getConnection();
            ret = booleanTest1(bool,  conn);
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void booleanTest2(TestPackageBooleanTest2 ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" + 
                "DECLARE " + 
                "  BOOL_IN BOOLEAN := sys.diutil.int_to_bool(:iBOOL_IN); " + 
                "  BOOL_OUT BOOLEAN; " + 
                "  BOOL_INOUT BOOLEAN := sys.diutil.int_to_bool(:iBOOL_INOUT); " + 
                "BEGIN " + 
                "  TEST_PACKAGE.BOOLEAN_TEST_2( " + 
                "    N => :N" + 
                "   ,BOOL_IN => BOOL_IN" + 
                "   ,BOOL_OUT => BOOL_OUT" + 
                "   ,BOOL_INOUT => BOOL_INOUT" + 
                "   );" + 
                "  :oBOOL_OUT := sys.diutil.bool_to_int(BOOL_OUT);" + 
                "  :oBOOL_INOUT := sys.diutil.bool_to_int(BOOL_INOUT);" + 
                "END;" + 
"");
                    // Set BOOL_IN from context boolIn
            if (ctx.getBoolIn() != null) {
                ocs.setInt(1, ctx.getBoolIn() ? 1 : 0);
            } else {
                ocs.setNull(1, Types.INTEGER);
            }

                    // Set BOOL_INOUT from context boolInout
            if (ctx.getBoolInout() != null) {
                ocs.setInt(2, ctx.getBoolInout() ? 1 : 0);
            } else {
                ocs.setNull(2, Types.INTEGER);
            }

                    // Set N from context n
            if (ctx.getN() != null) {
                ocs.setBigDecimal(3, ctx.getN());
            } else {
                ocs.setNull(3, Types.NUMERIC);
            }

        ocs.registerOutParameter(3, Types.NUMERIC); // N
        ocs.registerOutParameter(4, Types.INTEGER); // BOOL_OUT
        ocs.registerOutParameter(5, Types.INTEGER); // BOOL_INOUT
        ocs.execute();
        ctx.setN(ocs.getBigDecimal(3)); // N
        ctx.setBoolOut(null == ocs.getBigDecimal(4) ? null : BigDecimal.ONE.equals(ocs.getBigDecimal(4)) ? true : BigDecimal.ZERO.equals(ocs.getBigDecimal(4)) ? false : null); // BOOL_OUT
        ctx.setBoolInout(null == ocs.getBigDecimal(5) ? null : BigDecimal.ONE.equals(ocs.getBigDecimal(5)) ? true : BigDecimal.ZERO.equals(ocs.getBigDecimal(5)) ? false : null); // BOOL_INOUT
        ocs.close();
    }

    public static TestPackageBooleanTest2 booleanTest2(BigDecimal n, Boolean boolIn, Boolean boolInout,  Connection connection) throws SQLException {
        TestPackageBooleanTest2 ctx = new TestPackageBooleanTest2();
        ctx.setN(n);
        ctx.setBoolIn(boolIn);
        ctx.setBoolInout(boolInout);

        booleanTest2(ctx, connection);

        return ctx;
    }

    public static TestPackageBooleanTest2 booleanTest2(BigDecimal n, Boolean boolIn, Boolean boolInout,  DataSource dataSource) {
        Connection conn = null;
        TestPackageBooleanTest2 ret = null;

        try {
            conn = dataSource.getConnection();
            ret = booleanTest2(n, boolIn, boolInout,  conn);
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void combinedTypesTest1(TestPackageCombinedTypesTest1 ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" + 
                "DECLARE " + 
                "BEGIN " + 
                "  TEST_PACKAGE.COMBINED_TYPES_TEST_1( " + 
                "    P_OBJECT_TYPE => :P_OBJECT_TYPE" + 
                "   ,P_LIST_OF => :P_LIST_OF" + 
                "   );" + 
                "END;" + 
"");
                    // Set P_OBJECT_TYPE from context objectType
            if (ctx.getObjectType() != null) {
                ocs.setObject(1, SampleTypeOneConverter.getStruct(ctx.getObjectType(), connection));
            } else {
                ocs.setNull(1, Types.STRUCT, "SAMPLE_TYPE_ONE");
            }

        ocs.registerOutParameter(1, Types.STRUCT, "SAMPLE_TYPE_ONE"); // P_OBJECT_TYPE
                    // Set P_LIST_OF from context listOf
            ocs.setObject(2, SampleTypeOneConverter.getListArray(ctx.getListOf(), connection, "SAMPLE_TYPE_ONE_LIST"));
        ocs.registerOutParameter(2, Types.ARRAY, "SAMPLE_TYPE_ONE_LIST"); // P_LIST_OF
        ocs.execute();
        ctx.setObjectType(SampleTypeOneConverter.getObject((Struct)ocs.getObject(1))); // P_OBJECT_TYPE
        ctx.setListOf(SampleTypeOneConverter.getObjectList((Array)ocs.getObject(2))); // P_LIST_OF
        ocs.close();
    }

    public static TestPackageCombinedTypesTest1 combinedTypesTest1(SampleTypeOne objectType, List<SampleTypeOne> listOf,  Connection connection) throws SQLException {
        TestPackageCombinedTypesTest1 ctx = new TestPackageCombinedTypesTest1();
        ctx.setObjectType(objectType);
        ctx.setListOf(listOf);

        combinedTypesTest1(ctx, connection);

        return ctx;
    }

    public static TestPackageCombinedTypesTest1 combinedTypesTest1(SampleTypeOne objectType, List<SampleTypeOne> listOf,  DataSource dataSource) {
        Connection conn = null;
        TestPackageCombinedTypesTest1 ret = null;

        try {
            conn = dataSource.getConnection();
            ret = combinedTypesTest1(objectType, listOf,  conn);
        } catch (SQLException e) {
            e.printStackTrace();
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
        CallableStatement ocs = connection.prepareCall(                "" + 
                "DECLARE " + 
                "BEGIN " + 
                "  :result := " + 
                "  TEST_PACKAGE.GET_SYSDATE( " + 
                "   );" + 
                "END;" + 
"");
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
            e.printStackTrace();
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
        CallableStatement ocs = connection.prepareCall(                "" + 
                "DECLARE " + 
                "BEGIN " + 
                "  TEST_PACKAGE.GET_SYSDATE( " + 
                "    P_SYSDATE => :P_SYSDATE" + 
                "   );" + 
                "END;" + 
"");
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
            e.printStackTrace();
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
        CallableStatement ocs = connection.prepareCall(                "" + 
                "DECLARE " + 
                "BEGIN " + 
                "  TEST_PACKAGE.HELLO_WORLD( " + 
                "    P_NAME => :P_NAME" + 
                "   ,P_OUT => :P_OUT" + 
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
            e.printStackTrace();
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

    public static void objectTypeTest1(TestPackageObjectTypeTest1 ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" + 
                "DECLARE " + 
                "BEGIN " + 
                "  :result := " + 
                "  TEST_PACKAGE.OBJECT_TYPE_TEST_1( " + 
                "    P_OBJECT_TYPE => :P_OBJECT_TYPE" + 
                "   );" + 
                "END;" + 
"");
        ocs.registerOutParameter(1, Types.NUMERIC); // null
                    // Set P_OBJECT_TYPE from context objectType
            if (ctx.getObjectType() != null) {
                ocs.setObject(2, SampleTypeOneConverter.getStruct(ctx.getObjectType(), connection));
            } else {
                ocs.setNull(2, Types.STRUCT, "SAMPLE_TYPE_ONE");
            }

        ocs.registerOutParameter(2, Types.STRUCT, "SAMPLE_TYPE_ONE"); // P_OBJECT_TYPE
        ocs.execute();
        ctx.setFunctionReturn(ocs.getBigDecimal(1)); // null
        ctx.setObjectType(SampleTypeOneConverter.getObject((Struct)ocs.getObject(2))); // P_OBJECT_TYPE
        ocs.close();
    }

    public static TestPackageObjectTypeTest1 objectTypeTest1(SampleTypeOne objectType,  Connection connection) throws SQLException {
        TestPackageObjectTypeTest1 ctx = new TestPackageObjectTypeTest1();
        ctx.setObjectType(objectType);

        objectTypeTest1(ctx, connection);

        return ctx;
    }

    public static TestPackageObjectTypeTest1 objectTypeTest1(SampleTypeOne objectType,  DataSource dataSource) {
        Connection conn = null;
        TestPackageObjectTypeTest1 ret = null;

        try {
            conn = dataSource.getConnection();
            ret = objectTypeTest1(objectType,  conn);
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void tableOfTest1(TestPackageTableOfTest1 ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" + 
                "DECLARE " + 
                "BEGIN " + 
                "  :result := " + 
                "  TEST_PACKAGE.TABLE_OF_TEST_1( " + 
                "    P_LIST_OF => :P_LIST_OF" + 
                "   );" + 
                "END;" + 
"");
        ocs.registerOutParameter(1, Types.NUMERIC); // null
                    // Set P_LIST_OF from context listOf
            ocs.setObject(2, SampleTypeOneConverter.getListArray(ctx.getListOf(), connection, "SAMPLE_TYPE_ONE_LIST"));
        ocs.registerOutParameter(2, Types.ARRAY, "SAMPLE_TYPE_ONE_LIST"); // P_LIST_OF
        ocs.execute();
        ctx.setFunctionReturn(ocs.getBigDecimal(1)); // null
        ctx.setListOf(SampleTypeOneConverter.getObjectList((Array)ocs.getObject(2))); // P_LIST_OF
        ocs.close();
    }

    public static TestPackageTableOfTest1 tableOfTest1(List<SampleTypeOne> listOf,  Connection connection) throws SQLException {
        TestPackageTableOfTest1 ctx = new TestPackageTableOfTest1();
        ctx.setListOf(listOf);

        tableOfTest1(ctx, connection);

        return ctx;
    }

    public static TestPackageTableOfTest1 tableOfTest1(List<SampleTypeOne> listOf,  DataSource dataSource) {
        Connection conn = null;
        TestPackageTableOfTest1 ret = null;

        try {
            conn = dataSource.getConnection();
            ret = tableOfTest1(listOf,  conn);
        } catch (SQLException e) {
            e.printStackTrace();
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
