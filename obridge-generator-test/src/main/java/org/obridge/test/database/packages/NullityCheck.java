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
public final class NullityCheck {

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(NullityCheck.class.getName());

    private NullityCheck() {
    }


    public static void checkOutNullList(NullityCheckCheckOutNullList ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"NULLITY_CHECK\".\"CHECK_OUT_NULL_LIST\"( " +
                                      "    \"P_LIST_OBJECT\" => :P_LIST_OBJECT" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            ocs.registerOutParameter(1, Types.ARRAY, "SAMPLE_TYPE_ONE_LIST"); // P_LIST_OBJECT
            LOGGER.info("NullityCheck.checkOutNullList called");
            ocs.execute();
            LOGGER.info("NullityCheck.checkOutNullList executed");
            ctx.setListObject(SampleTypeOneConverter.getObjectList((Array)ocs.getObject(1))); // P_LIST_OBJECT
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static NullityCheckCheckOutNullList checkOutNullList( Connection connection) {
    final NullityCheckCheckOutNullList ctx = new NullityCheckCheckOutNullList();
    checkOutNullList(ctx, connection);
    return ctx;
}

    public static NullityCheckCheckOutNullList checkOutNullList( DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return checkOutNullList( conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void checkOutNullList(DataSource dataSource, NullityCheckCheckOutNullList ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            checkOutNullList(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void checkOutEmptyList(NullityCheckCheckOutEmptyList ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"NULLITY_CHECK\".\"CHECK_OUT_EMPTY_LIST\"( " +
                                      "    \"P_LIST_OBJECT\" => :P_LIST_OBJECT" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            ocs.registerOutParameter(1, Types.ARRAY, "SAMPLE_TYPE_ONE_LIST"); // P_LIST_OBJECT
            LOGGER.info("NullityCheck.checkOutEmptyList called");
            ocs.execute();
            LOGGER.info("NullityCheck.checkOutEmptyList executed");
            ctx.setListObject(SampleTypeOneConverter.getObjectList((Array)ocs.getObject(1))); // P_LIST_OBJECT
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static NullityCheckCheckOutEmptyList checkOutEmptyList( Connection connection) {
    final NullityCheckCheckOutEmptyList ctx = new NullityCheckCheckOutEmptyList();
    checkOutEmptyList(ctx, connection);
    return ctx;
}

    public static NullityCheckCheckOutEmptyList checkOutEmptyList( DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return checkOutEmptyList( conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void checkOutEmptyList(DataSource dataSource, NullityCheckCheckOutEmptyList ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            checkOutEmptyList(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void checkOutNullObject(NullityCheckCheckOutNullObject ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"NULLITY_CHECK\".\"CHECK_OUT_NULL_OBJECT\"( " +
                                      "    \"P_SAMPLE_OBJECT\" => :P_SAMPLE_OBJECT" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            ocs.registerOutParameter(1, Types.STRUCT, "SAMPLE_TYPE_ONE"); // P_SAMPLE_OBJECT
            LOGGER.info("NullityCheck.checkOutNullObject called");
            ocs.execute();
            LOGGER.info("NullityCheck.checkOutNullObject executed");
            ctx.setSampleObject(SampleTypeOneConverter.getObject((Struct)ocs.getObject(1))); // P_SAMPLE_OBJECT
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static NullityCheckCheckOutNullObject checkOutNullObject( Connection connection) {
    final NullityCheckCheckOutNullObject ctx = new NullityCheckCheckOutNullObject();
    checkOutNullObject(ctx, connection);
    return ctx;
}

    public static NullityCheckCheckOutNullObject checkOutNullObject( DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return checkOutNullObject( conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void checkOutNullObject(DataSource dataSource, NullityCheckCheckOutNullObject ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            checkOutNullObject(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



    public static void checkOutEmptyObject(NullityCheckCheckOutEmptyObject ctx, Connection connection) {
    try {
        final CallableStatement ocs = connection.prepareCall(                "" +
                                      "DECLARE " +
                                      "BEGIN " +
                                      "  \"NULLITY_CHECK\".\"CHECK_OUT_EMPTY_OBJECT\"( " +
                                      "    \"P_SAMPLE_OBJECT\" => :P_SAMPLE_OBJECT" +
                                      "   );" +
                                      "END;" +
                                      "");
        try {
            ocs.registerOutParameter(1, Types.STRUCT, "SAMPLE_TYPE_ONE"); // P_SAMPLE_OBJECT
            LOGGER.info("NullityCheck.checkOutEmptyObject called");
            ocs.execute();
            LOGGER.info("NullityCheck.checkOutEmptyObject executed");
            ctx.setSampleObject(SampleTypeOneConverter.getObject((Struct)ocs.getObject(1))); // P_SAMPLE_OBJECT
        } finally  {
            ocs.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}

    public static NullityCheckCheckOutEmptyObject checkOutEmptyObject( Connection connection) {
    final NullityCheckCheckOutEmptyObject ctx = new NullityCheckCheckOutEmptyObject();
    checkOutEmptyObject(ctx, connection);
    return ctx;
}

    public static NullityCheckCheckOutEmptyObject checkOutEmptyObject( DataSource dataSource) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            return checkOutEmptyObject( conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}


    public static void checkOutEmptyObject(DataSource dataSource, NullityCheckCheckOutEmptyObject ctx) {
    try {
        final Connection conn = dataSource.getConnection();
        try {
            checkOutEmptyObject(ctx, conn);
        } finally {
            conn.close();
        }
    } catch (SQLException e) {
        throw new StoredProcedureCallException(e);
    }
}



}
