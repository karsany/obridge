package org.obridge.test.database.packages;

import org.obridge.test.database.context.*;
import org.obridge.test.database.converters.*;
import org.obridge.test.database.objects.*;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Arrays;

public class SimpleProcedures {


    public static void a(SimpleProceduresA ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" +
                                "DECLARE " +
                                "BEGIN " +
                                "  \"SIMPLE_PROCEDURES\".\"A\"( " +
                                "   );" +
                                "END;" +
                                "");
        ocs.execute();
        ocs.close();
    }

    public static SimpleProceduresA a( Connection connection) throws SQLException {
        SimpleProceduresA ctx = new SimpleProceduresA();
        a(ctx, connection);
        return ctx;
    }

    public static SimpleProceduresA a( DataSource dataSource) {
        Connection conn = null;
        SimpleProceduresA ret = null;
        try {
            conn = dataSource.getConnection();
            return a( conn);
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


    public static void allTypes(SimpleProceduresAllTypes ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" +
                                "DECLARE " +
                                "  B BOOLEAN := sys.diutil.int_to_bool(:iB); " +
                                "BEGIN " +
                                "  \"SIMPLE_PROCEDURES\".\"ALL_TYPES\"( " +
                                "    \"N\" => :N" +
                                "   ,\"BI\" => :BI" +
                                "   ,\"PI\" => :PI" +
                                "   ,\"VCH\" => :VCH" +
                                "   ,\"NVCH\" => :NVCH" +
                                "   ,\"CH\" => :CH" +
                                "   ,\"NCH\" => :NCH" +
                                "   ,\"D\" => :D" +
                                "   ,\"TS\" => :TS" +
                                "   ,\"CL\" => :CL" +
                                "   ,\"B\" => B" +
                                "   ,\"TBL\" => :TBL" +
                                "   ,\"O\" => :O" +
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

    public static SimpleProceduresAllTypes allTypes(BigDecimal n, Integer bi, Integer pi, String vch, String nvch, String ch, String nch, Date d, Timestamp ts, String cl, Boolean b, List<SampleTypeOne> tbl, SampleTypeOne o,  Connection connection) throws SQLException {
        SimpleProceduresAllTypes ctx = new SimpleProceduresAllTypes();
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

    public static SimpleProceduresAllTypes allTypes(BigDecimal n, Integer bi, Integer pi, String vch, String nvch, String ch, String nch, Date d, Timestamp ts, String cl, Boolean b, List<SampleTypeOne> tbl, SampleTypeOne o,  DataSource dataSource) {
        Connection conn = null;
        SimpleProceduresAllTypes ret = null;
        try {
            conn = dataSource.getConnection();
            return allTypes(n, bi, pi, vch, nvch, ch, nch, d, ts, cl, b, tbl, o,  conn);
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


    public static void funcWithTypes(SimpleProceduresFuncWithTypes ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" +
                                "DECLARE " +
                                "BEGIN " +
                                "  \"SIMPLE_PROCEDURES\".\"FUNC_WITH_TYPES\"( " +
                                "    \"P_PARAM_1\" => :P_PARAM_1" +
                                "   ,\"P_PARAM_HELLO\" => :P_PARAM_HELLO" +
                                "   ,\"P_PARAM_TWO\" => :P_PARAM_TWO" +
                                "   );" +
                                "END;" +
                                "");
        // Set P_PARAM_1 from context param1
        if (ctx.getParam1() != null) {
            ocs.setObject(1, SampleTypeOneConverter.getStruct(ctx.getParam1(), connection));
        } else {
            ocs.setNull(1, Types.STRUCT, "SAMPLE_TYPE_ONE");
        }
        // Set P_PARAM_HELLO from context paramHello
        ocs.setObject(2, SampleTypeOneConverter.getListArray(ctx.getParamHello(), connection, "SAMPLE_TYPE_ONE_LIST"));
        ocs.registerOutParameter(3, Types.STRUCT, "SAMPLE_TYPE_TWO"); // P_PARAM_TWO
        ocs.execute();
        ctx.setParamTwo(SampleTypeTwoConverter.getObject((Struct)ocs.getObject(3))); // P_PARAM_TWO
        ocs.close();
    }

    public static SimpleProceduresFuncWithTypes funcWithTypes(SampleTypeOne param1, List<SampleTypeOne> paramHello,  Connection connection) throws SQLException {
        SimpleProceduresFuncWithTypes ctx = new SimpleProceduresFuncWithTypes();
        ctx.setParam1(param1);
        ctx.setParamHello(paramHello);
        funcWithTypes(ctx, connection);
        return ctx;
    }

    public static SimpleProceduresFuncWithTypes funcWithTypes(SampleTypeOne param1, List<SampleTypeOne> paramHello,  DataSource dataSource) {
        Connection conn = null;
        SimpleProceduresFuncWithTypes ret = null;
        try {
            conn = dataSource.getConnection();
            return funcWithTypes(param1, paramHello,  conn);
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


    public static void overload1(SimpleProceduresOverload1 ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" +
                                "DECLARE " +
                                "BEGIN " +
                                "  \"SIMPLE_PROCEDURES\".\"OVERLOAD\"( " +
                                "   );" +
                                "END;" +
                                "");
        ocs.execute();
        ocs.close();
    }

    public static SimpleProceduresOverload1 overload1( Connection connection) throws SQLException {
        SimpleProceduresOverload1 ctx = new SimpleProceduresOverload1();
        overload1(ctx, connection);
        return ctx;
    }

    public static SimpleProceduresOverload1 overload1( DataSource dataSource) {
        Connection conn = null;
        SimpleProceduresOverload1 ret = null;
        try {
            conn = dataSource.getConnection();
            return overload1( conn);
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


    public static void overload2(SimpleProceduresOverload2 ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" +
                                "DECLARE " +
                                "BEGIN " +
                                "  \"SIMPLE_PROCEDURES\".\"OVERLOAD\"( " +
                                "    \"A\" => :A" +
                                "   );" +
                                "END;" +
                                "");
        // Set A from context a
        if (ctx.getA() != null) {
            ocs.setString(1, ctx.getA());
        } else {
            ocs.setNull(1, Types.VARCHAR);
        }
        ocs.execute();
        ocs.close();
    }

    public static SimpleProceduresOverload2 overload2(String a,  Connection connection) throws SQLException {
        SimpleProceduresOverload2 ctx = new SimpleProceduresOverload2();
        ctx.setA(a);
        overload2(ctx, connection);
        return ctx;
    }

    public static SimpleProceduresOverload2 overload2(String a,  DataSource dataSource) {
        Connection conn = null;
        SimpleProceduresOverload2 ret = null;
        try {
            conn = dataSource.getConnection();
            return overload2(a,  conn);
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


    public static void procWithLists(SimpleProceduresProcWithLists ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" +
                                "DECLARE " +
                                "BEGIN " +
                                "  \"SIMPLE_PROCEDURES\".\"PROC_WITH_LISTS\"( " +
                                "    \"P1\" => :P1" +
                                "   ,\"P2\" => :P2" +
                                "   ,\"P3\" => :P3" +
                                "   );" +
                                "END;" +
                                "");
        // Set P1 from context p1
        ocs.setObject(1, SampleTypeOneConverter.getListArray(ctx.getP1(), connection, "SAMPLE_TYPE_ONE_LIST"));
        ocs.registerOutParameter(1, Types.ARRAY, "SAMPLE_TYPE_ONE_LIST"); // P1
        // Set P2 from context p2
        ocs.setObject(2, SampleTypeTwoConverter.getListArray(ctx.getP2(), connection, "SAMPLE_TYPE_TWO_GROUP"));
        ocs.registerOutParameter(2, Types.ARRAY, "SAMPLE_TYPE_TWO_GROUP"); // P2
        // Set P3 from context p3
        ocs.setObject(3, SampleTypeTwoConverter.getListArray(ctx.getP3(), connection, "SAMPLE_TYPE_TWO_LIST"));
        ocs.registerOutParameter(3, Types.ARRAY, "SAMPLE_TYPE_TWO_LIST"); // P3
        ocs.execute();
        ctx.setP1(SampleTypeOneConverter.getObjectList((Array)ocs.getObject(1))); // P1
        ctx.setP2(SampleTypeTwoConverter.getObjectList((Array)ocs.getObject(2))); // P2
        ctx.setP3(SampleTypeTwoConverter.getObjectList((Array)ocs.getObject(3))); // P3
        ocs.close();
    }

    public static SimpleProceduresProcWithLists procWithLists(List<SampleTypeOne> p1, List<SampleTypeTwo> p2, List<SampleTypeTwo> p3,  Connection connection) throws SQLException {
        SimpleProceduresProcWithLists ctx = new SimpleProceduresProcWithLists();
        ctx.setP1(p1);
        ctx.setP2(p2);
        ctx.setP3(p3);
        procWithLists(ctx, connection);
        return ctx;
    }

    public static SimpleProceduresProcWithLists procWithLists(List<SampleTypeOne> p1, List<SampleTypeTwo> p2, List<SampleTypeTwo> p3,  DataSource dataSource) {
        Connection conn = null;
        SimpleProceduresProcWithLists ret = null;
        try {
            conn = dataSource.getConnection();
            return procWithLists(p1, p2, p3,  conn);
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


    public static void refcursorTest(SimpleProceduresRefcursorTest ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" +
                                "DECLARE " +
                                "BEGIN " +
                                "  \"SIMPLE_PROCEDURES\".\"REFCURSOR_TEST\"( " +
                                "    \"P_REFC\" => :P_REFC" +
                                "   );" +
                                "END;" +
                                "");
        ocs.registerOutParameter(1, -10); // P_REFC
        ocs.execute();
        ctx.setRefc((ResultSet)ocs.getObject(1)); // P_REFC
    }

    public static SimpleProceduresRefcursorTest refcursorTest( Connection connection) throws SQLException {
        SimpleProceduresRefcursorTest ctx = new SimpleProceduresRefcursorTest();
        refcursorTest(ctx, connection);
        return ctx;
    }

    public static SimpleProceduresRefcursorTest refcursorTest( DataSource dataSource) {
        Connection conn = null;
        SimpleProceduresRefcursorTest ret = null;
        try {
            conn = dataSource.getConnection();
            return refcursorTest( conn);
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


    public static void simpleFunc(SimpleProceduresSimpleFunc ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall(                "" +
                                "DECLARE " +
                                "BEGIN " +
                                "  :result := " +
                                "  \"SIMPLE_PROCEDURES\".\"SIMPLE_FUNC\"( " +
                                "    \"A\" => :A" +
                                "   ,\"B\" => :B" +
                                "   ,\"C\" => :C" +
                                "   );" +
                                "END;" +
                                "");
        ocs.registerOutParameter(1, Types.NUMERIC); // null
        // Set A from context a
        if (ctx.getA() != null) {
            ocs.setString(2, ctx.getA());
        } else {
            ocs.setNull(2, Types.VARCHAR);
        }
        // Set B from context b
        if (ctx.getB() != null) {
            ocs.setString(3, ctx.getB());
        } else {
            ocs.setNull(3, Types.VARCHAR);
        }
        ocs.registerOutParameter(3, Types.VARCHAR); // B
        ocs.registerOutParameter(4, Types.VARCHAR); // C
        ocs.execute();
        ctx.setFunctionReturn(ocs.getBigDecimal(1)); // null
        ctx.setB(ocs.getString(3)); // B
        ctx.setC(ocs.getString(4)); // C
        ocs.close();
    }

    public static SimpleProceduresSimpleFunc simpleFunc(String a, String b,  Connection connection) throws SQLException {
        SimpleProceduresSimpleFunc ctx = new SimpleProceduresSimpleFunc();
        ctx.setA(a);
        ctx.setB(b);
        simpleFunc(ctx, connection);
        return ctx;
    }

    public static SimpleProceduresSimpleFunc simpleFunc(String a, String b,  DataSource dataSource) {
        Connection conn = null;
        SimpleProceduresSimpleFunc ret = null;
        try {
            conn = dataSource.getConnection();
            return simpleFunc(a, b,  conn);
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
