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
public final class BlobTest {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(BlobTest.class.getName());

    private BlobTest() {
    }


    public static void testcase1(BlobTestTestcase1 ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"BLOB_TEST\".\"TESTCASE1\"( " +
                                      "    \"BLB\" => :BLB" +
                                      "   ,\"TP_BLB\" => :TP_BLB" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            // Set BLB from context blb
            if (ctx.getBlb() != null) {
                ocs.setBytes(1, ctx.getBlb());
            } else {
                ocs.setNull(1, Types.VARBINARY);
            }
            ocs.registerOutParameter(2, Types.STRUCT, "TP_BLOB_TEST"); // TP_BLB
            LOGGER.info("BlobTest.testcase1 called");
            ocs.execute();
            LOGGER.info("BlobTest.testcase1 executed");
            ctx.setTpBlb(TpBlobTestConverter.getObject((Struct)ocs.getObject(2))); // TP_BLB
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static BlobTestTestcase1 testcase1(byte[] blb,  Connection connection) {
    final BlobTestTestcase1 ctx = new BlobTestTestcase1();
    ctx.setBlb(blb);
    testcase1(ctx, connection);
    return ctx;
}

    public static BlobTestTestcase1 testcase1(byte[] blb,  DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return testcase1(blb,  conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void testcase1(DataSource dataSource, BlobTestTestcase1 ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            testcase1(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void testcase2(BlobTestTestcase2 ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"BLOB_TEST\".\"TESTCASE2\"( " +
                                      "    \"TP_BLB\" => :TP_BLB" +
                                      "   ,\"SIZ\" => :SIZ" +
                                      "   ,\"BLB\" => :BLB" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            // Set TP_BLB from context tpBlb
            if (ctx.getTpBlb() != null) {
                ocs.setObject(1, TpBlobTestConverter.getStruct(ctx.getTpBlb(), connection));
            } else {
                ocs.setNull(1, Types.STRUCT, "TP_BLOB_TEST");
            }
            ocs.registerOutParameter(2, Types.NUMERIC); // SIZ
            ocs.registerOutParameter(3, Types.VARBINARY); // BLB
            LOGGER.info("BlobTest.testcase2 called");
            ocs.execute();
            LOGGER.info("BlobTest.testcase2 executed");
            ctx.setSiz(ocs.getBigDecimal(2)); // SIZ
            ctx.setBlb(ocs.getBytes(3)); // BLB
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static BlobTestTestcase2 testcase2(TpBlobTest tpBlb,  Connection connection) {
    final BlobTestTestcase2 ctx = new BlobTestTestcase2();
    ctx.setTpBlb(tpBlb);
    testcase2(ctx, connection);
    return ctx;
}

    public static BlobTestTestcase2 testcase2(TpBlobTest tpBlb,  DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return testcase2(tpBlb,  conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void testcase2(DataSource dataSource, BlobTestTestcase2 ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            testcase2(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



}
