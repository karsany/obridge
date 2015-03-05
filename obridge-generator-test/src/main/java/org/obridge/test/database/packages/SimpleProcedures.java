package org.obridge.test.database.packages;

import java.sql.*;
import javax.sql.DataSource;
import org.obridge.test.database.context.*;
import org.obridge.test.database.converters.*;
import org.obridge.test.database.objects.*;
import java.util.List;
import java.math.BigDecimal;


public class SimpleProcedures {

    public static void a(SimpleProceduresA ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall("{ CALL SIMPLE_PROCEDURES.A(  ) }");
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
            ret = a( conn);

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

    public static void allTypes(SimpleProceduresAllTypes ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall("{ CALL SIMPLE_PROCEDURES.ALL_TYPES( N => ? , BI => ? , PI => ? , VCH => ? , NVCH => ? , CH => ? , NCH => ? , D => ? , TS => ? , CL => ? , B => ? , TBL => ? , O => ? ) }");
        ocs.setBigDecimal(1, ctx.getN()); // N
        ocs.registerOutParameter(1, Types.NUMERIC); // N
        ocs.setInt(2, ctx.getBi()); // BI
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
        ocs.setObject(13, SampleTypeOneConverter.getStruct(ctx.getO(),connection)); // O
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
        ctx.setTbl(SampleTypeOneConverter.getObjectList((Array)ocs.getObject(12))); // TBL
        ctx.setO(SampleTypeOneConverter.getObject((Struct)ocs.getObject(13))); // O
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
            ret = allTypes(n, bi, pi, vch, nvch, ch, nch, d, ts, cl, b, tbl, o,  conn);

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

    public static void funcWithTypes(SimpleProceduresFuncWithTypes ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall("{ CALL SIMPLE_PROCEDURES.FUNC_WITH_TYPES( P_PARAM_1 => ? , P_PARAM_HELLO => ? , P_PARAM_TWO => ? ) }");
        ocs.setObject(1, SampleTypeOneConverter.getStruct(ctx.getParam1(),connection)); // P_PARAM_1
        ocs.setObject(2, SampleTypeOneConverter.getListArray(ctx.getParamHello(), connection)); // P_PARAM_HELLO
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
            ret = funcWithTypes(param1, paramHello,  conn);

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

    public static void overload1(SimpleProceduresOverload1 ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall("{ CALL SIMPLE_PROCEDURES.OVERLOAD(  ) }");
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
            ret = overload1( conn);

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

    public static void overload2(SimpleProceduresOverload2 ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall("{ CALL SIMPLE_PROCEDURES.OVERLOAD( A => ? ) }");
        ocs.setString(1, ctx.getA()); // A
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
            ret = overload2(a,  conn);

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

    public static void procWithLists(SimpleProceduresProcWithLists ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall("{ CALL SIMPLE_PROCEDURES.PROC_WITH_LISTS( P1 => ? , P2 => ? , P3 => ? ) }");
        ocs.setObject(1, SampleTypeOneConverter.getListArray(ctx.getP1(), connection)); // P1
        ocs.registerOutParameter(1, Types.ARRAY, "SAMPLE_TYPE_ONE"); // P1
        ocs.setObject(2, SampleTypeTwoConverter.getListArray(ctx.getP2(), connection)); // P2
        ocs.registerOutParameter(2, Types.ARRAY, "SAMPLE_TYPE_TWO"); // P2
        ocs.setObject(3, SampleTypeTwoConverter.getListArray(ctx.getP3(), connection)); // P3
        ocs.registerOutParameter(3, Types.ARRAY, "SAMPLE_TYPE_TWO"); // P3
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
            ret = procWithLists(p1, p2, p3,  conn);

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

    public static void simpleFunc(SimpleProceduresSimpleFunc ctx, Connection connection) throws SQLException {
        CallableStatement ocs = connection.prepareCall("{ CALL ? := SIMPLE_PROCEDURES.SIMPLE_FUNC( A => ? , B => ? , C => ? ) }");
        ocs.registerOutParameter(1, Types.NUMERIC); // null
        ocs.setString(2, ctx.getA()); // A
        ocs.setString(3, ctx.getB()); // B
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
            ret = simpleFunc(a, b,  conn);

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
