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
public final class SimpleProcedures {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(SimpleProcedures.class.getName());

    private SimpleProcedures() {
    }


    public static void a(SimpleProceduresA ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"SIMPLE_PROCEDURES\".\"A\"( " +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            LOGGER.info("SimpleProcedures.a called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.a executed");
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresA a( Connection connection) {
    final SimpleProceduresA ctx = new SimpleProceduresA();
    a(ctx, connection);
    return ctx;
}

    public static SimpleProceduresA a( DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return a( conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void a(DataSource dataSource, SimpleProceduresA ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            a(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void overload1(SimpleProceduresOverload1 ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"SIMPLE_PROCEDURES\".\"OVERLOAD\"( " +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            LOGGER.info("SimpleProcedures.overload1 called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.overload1 executed");
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresOverload1 overload1( Connection connection) {
    final SimpleProceduresOverload1 ctx = new SimpleProceduresOverload1();
    overload1(ctx, connection);
    return ctx;
}

    public static SimpleProceduresOverload1 overload1( DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return overload1( conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void overload1(DataSource dataSource, SimpleProceduresOverload1 ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            overload1(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void overload2(SimpleProceduresOverload2 ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"SIMPLE_PROCEDURES\".\"OVERLOAD\"( " +
                                      "    \"A\" => :A" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            // Set A from context a
            if (ctx.getA() != null) {
                ocs.setString(1, ctx.getA());
            } else {
                ocs.setNull(1, Types.VARCHAR);
            }
            LOGGER.info("SimpleProcedures.overload2 called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.overload2 executed");
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresOverload2 overload2(String a,  Connection connection) {
    final SimpleProceduresOverload2 ctx = new SimpleProceduresOverload2();
    ctx.setA(a);
    overload2(ctx, connection);
    return ctx;
}

    public static SimpleProceduresOverload2 overload2(String a,  DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return overload2(a,  conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void overload2(DataSource dataSource, SimpleProceduresOverload2 ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            overload2(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void simpleFunc(SimpleProceduresSimpleFunc ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
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
        try {
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
            LOGGER.info("SimpleProcedures.simpleFunc called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.simpleFunc executed");
            ctx.setFunctionReturn(ocs.getBigDecimal(1)); // null
            ctx.setB(ocs.getString(3)); // B
            ctx.setC(ocs.getString(4)); // C
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresSimpleFunc simpleFunc(String a, String b,  Connection connection) {
    final SimpleProceduresSimpleFunc ctx = new SimpleProceduresSimpleFunc();
    ctx.setA(a);
    ctx.setB(b);
    simpleFunc(ctx, connection);
    return ctx;
}

    public static SimpleProceduresSimpleFunc simpleFunc(String a, String b,  DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return simpleFunc(a, b,  conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void simpleFunc(DataSource dataSource, SimpleProceduresSimpleFunc ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            simpleFunc(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void funcWithTypes(SimpleProceduresFuncWithTypes ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"SIMPLE_PROCEDURES\".\"FUNC_WITH_TYPES\"( " +
                                      "    \"P_PARAM_1\" => :P_PARAM_1" +
                                      "   ,\"P_PARAM_HELLO\" => :P_PARAM_HELLO" +
                                      "   ,\"P_PARAM_TWO\" => :P_PARAM_TWO" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            // Set P_PARAM_1 from context param1
            if (ctx.getParam1() != null) {
                ocs.setObject(1, SampleTypeOneConverter.getStruct(ctx.getParam1(), connection));
            } else {
                ocs.setNull(1, Types.STRUCT, "SAMPLE_TYPE_ONE");
            }
            // Set P_PARAM_HELLO from context paramHello
            ocs.setObject(2, SampleTypeOneConverter.getListArray(ctx.getParamHello(), connection, "SAMPLE_TYPE_ONE_LIST"));
            ocs.registerOutParameter(3, Types.STRUCT, "SAMPLE_TYPE_TWO"); // P_PARAM_TWO
            LOGGER.info("SimpleProcedures.funcWithTypes called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.funcWithTypes executed");
            ctx.setParamTwo(SampleTypeTwoConverter.getObject((Struct)ocs.getObject(3))); // P_PARAM_TWO
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresFuncWithTypes funcWithTypes(SampleTypeOne param1, List<SampleTypeOne> paramHello,  Connection connection) {
    final SimpleProceduresFuncWithTypes ctx = new SimpleProceduresFuncWithTypes();
    ctx.setParam1(param1);
    ctx.setParamHello(paramHello);
    funcWithTypes(ctx, connection);
    return ctx;
}

    public static SimpleProceduresFuncWithTypes funcWithTypes(SampleTypeOne param1, List<SampleTypeOne> paramHello,  DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return funcWithTypes(param1, paramHello,  conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void funcWithTypes(DataSource dataSource, SimpleProceduresFuncWithTypes ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            funcWithTypes(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void allTypes(SimpleProceduresAllTypes ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
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
        try {
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
            LOGGER.info("SimpleProcedures.allTypes called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.allTypes executed");
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
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresAllTypes allTypes(BigDecimal n, Integer bi, Integer pi, String vch, String nvch, String ch, String nch, Date d, Timestamp ts, String cl, Boolean b, List<SampleTypeOne> tbl, SampleTypeOne o,  Connection connection) {
    final SimpleProceduresAllTypes ctx = new SimpleProceduresAllTypes();
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
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return allTypes(n, bi, pi, vch, nvch, ch, nch, d, ts, cl, b, tbl, o,  conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void allTypes(DataSource dataSource, SimpleProceduresAllTypes ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            allTypes(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void procWithLists(SimpleProceduresProcWithLists ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"SIMPLE_PROCEDURES\".\"PROC_WITH_LISTS\"( " +
                                      "    \"P1\" => :P1" +
                                      "   ,\"P2\" => :P2" +
                                      "   ,\"P3\" => :P3" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            // Set P1 from context p1
            ocs.setObject(1, SampleTypeOneConverter.getListArray(ctx.getP1(), connection, "SAMPLE_TYPE_ONE_LIST"));
            ocs.registerOutParameter(1, Types.ARRAY, "SAMPLE_TYPE_ONE_LIST"); // P1
            // Set P2 from context p2
            ocs.setObject(2, SampleTypeTwoConverter.getListArray(ctx.getP2(), connection, "SAMPLE_TYPE_TWO_GROUP"));
            ocs.registerOutParameter(2, Types.ARRAY, "SAMPLE_TYPE_TWO_GROUP"); // P2
            // Set P3 from context p3
            ocs.setObject(3, SampleTypeTwoConverter.getListArray(ctx.getP3(), connection, "SAMPLE_TYPE_TWO_LIST"));
            ocs.registerOutParameter(3, Types.ARRAY, "SAMPLE_TYPE_TWO_LIST"); // P3
            LOGGER.info("SimpleProcedures.procWithLists called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.procWithLists executed");
            ctx.setP1(SampleTypeOneConverter.getObjectList((Array)ocs.getObject(1))); // P1
            ctx.setP2(SampleTypeTwoConverter.getObjectList((Array)ocs.getObject(2))); // P2
            ctx.setP3(SampleTypeTwoConverter.getObjectList((Array)ocs.getObject(3))); // P3
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresProcWithLists procWithLists(List<SampleTypeOne> p1, List<SampleTypeTwo> p2, List<SampleTypeTwo> p3,  Connection connection) {
    final SimpleProceduresProcWithLists ctx = new SimpleProceduresProcWithLists();
    ctx.setP1(p1);
    ctx.setP2(p2);
    ctx.setP3(p3);
    procWithLists(ctx, connection);
    return ctx;
}

    public static SimpleProceduresProcWithLists procWithLists(List<SampleTypeOne> p1, List<SampleTypeTwo> p2, List<SampleTypeTwo> p3,  DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return procWithLists(p1, p2, p3,  conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void procWithLists(DataSource dataSource, SimpleProceduresProcWithLists ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            procWithLists(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void refcursorTest(SimpleProceduresRefcursorTest ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"SIMPLE_PROCEDURES\".\"REFCURSOR_TEST\"( " +
                                      "    \"P_REFC\" => :P_REFC" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            ocs.registerOutParameter(1, -10); // P_REFC
            LOGGER.info("SimpleProcedures.refcursorTest called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.refcursorTest executed");
            ctx.setRefc((ResultSet)ocs.getObject(1)); // P_REFC
        } finally  {
            // ResultSet as return parameter, not closing connection
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresRefcursorTest refcursorTest( Connection connection) {
    final SimpleProceduresRefcursorTest ctx = new SimpleProceduresRefcursorTest();
    refcursorTest(ctx, connection);
    return ctx;
}






    public static void rawTest(SimpleProceduresRawTest ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"SIMPLE_PROCEDURES\".\"RAW_TEST\"( " +
                                      "    \"P_R\" => :P_R" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            // Set P_R from context r
            if (ctx.getR() != null) {
                ocs.setBytes(1, ctx.getR());
            } else {
                ocs.setNull(1, Types.VARBINARY);
            }
            ocs.registerOutParameter(1, Types.VARBINARY); // P_R
            LOGGER.info("SimpleProcedures.rawTest called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.rawTest executed");
            ctx.setR(ocs.getBytes(1)); // P_R
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresRawTest rawTest(byte[] r,  Connection connection) {
    final SimpleProceduresRawTest ctx = new SimpleProceduresRawTest();
    ctx.setR(r);
    rawTest(ctx, connection);
    return ctx;
}

    public static SimpleProceduresRawTest rawTest(byte[] r,  DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return rawTest(r,  conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void rawTest(DataSource dataSource, SimpleProceduresRawTest ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            rawTest(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void rawInTypeTest(SimpleProceduresRawInTypeTest ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"SIMPLE_PROCEDURES\".\"RAW_IN_TYPE_TEST\"( " +
                                      "    \"P_TT\" => :P_TT" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            ocs.registerOutParameter(1, Types.STRUCT, "SAMPLE_TYPE_ONE"); // P_TT
            LOGGER.info("SimpleProcedures.rawInTypeTest called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.rawInTypeTest executed");
            ctx.setTt(SampleTypeOneConverter.getObject((Struct)ocs.getObject(1))); // P_TT
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresRawInTypeTest rawInTypeTest( Connection connection) {
    final SimpleProceduresRawInTypeTest ctx = new SimpleProceduresRawInTypeTest();
    rawInTypeTest(ctx, connection);
    return ctx;
}

    public static SimpleProceduresRawInTypeTest rawInTypeTest( DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return rawInTypeTest( conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void rawInTypeTest(DataSource dataSource, SimpleProceduresRawInTypeTest ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            rawInTypeTest(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void testTypeWithIntegerField(SimpleProceduresTestTypeWithIntegerField ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"SIMPLE_PROCEDURES\".\"TEST_TYPE_WITH_INTEGER_FIELD\"( " +
                                      "    \"P_TP\" => :P_TP" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            // Set P_TP from context tp
            if (ctx.getTp() != null) {
                ocs.setObject(1, SampleTypeThreeConverter.getStruct(ctx.getTp(), connection));
            } else {
                ocs.setNull(1, Types.STRUCT, "SAMPLE_TYPE_THREE");
            }
            ocs.registerOutParameter(1, Types.STRUCT, "SAMPLE_TYPE_THREE"); // P_TP
            LOGGER.info("SimpleProcedures.testTypeWithIntegerField called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.testTypeWithIntegerField executed");
            ctx.setTp(SampleTypeThreeConverter.getObject((Struct)ocs.getObject(1))); // P_TP
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresTestTypeWithIntegerField testTypeWithIntegerField(SampleTypeThree tp,  Connection connection) {
    final SimpleProceduresTestTypeWithIntegerField ctx = new SimpleProceduresTestTypeWithIntegerField();
    ctx.setTp(tp);
    testTypeWithIntegerField(ctx, connection);
    return ctx;
}

    public static SimpleProceduresTestTypeWithIntegerField testTypeWithIntegerField(SampleTypeThree tp,  DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return testTypeWithIntegerField(tp,  conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void testTypeWithIntegerField(DataSource dataSource, SimpleProceduresTestTypeWithIntegerField ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            testTypeWithIntegerField(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void raiseError(SimpleProceduresRaiseError ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"SIMPLE_PROCEDURES\".\"RAISE_ERROR\"( " +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            LOGGER.info("SimpleProcedures.raiseError called");
            ocs.execute();
            LOGGER.info("SimpleProcedures.raiseError executed");
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static SimpleProceduresRaiseError raiseError( Connection connection) {
    final SimpleProceduresRaiseError ctx = new SimpleProceduresRaiseError();
    raiseError(ctx, connection);
    return ctx;
}

    public static SimpleProceduresRaiseError raiseError( DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return raiseError( conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void raiseError(DataSource dataSource, SimpleProceduresRaiseError ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            raiseError(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



}
