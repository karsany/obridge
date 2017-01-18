package org.obridge.test.database.converters;

import org.obridge.test.database.objects.*;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@Generated("org.obridge.generators.ConverterObjectGenerator")
public final class TpBlobTestConverter {

    public static final String TYPE_NAME = "TP_BLOB_TEST";

    private TpBlobTestConverter() {
    }

    public static Struct getStruct(TpBlobTest o, Connection connection) throws SQLException {
    if (o == null) {
        return null;
    }
    List<Object> struct = new ArrayList<Object>();
    Blob bl0 = connection.createBlob();
    bl0.setBytes(1, o.getThisIsABlob());
    struct.add(0, bl0); // THIS_IS_A_BLOB
    struct.add(1, o.getBlobSize()); // BLOB_SIZE
    return connection.createStruct(TYPE_NAME, struct.toArray());
}

    public static Array getListArray(List<TpBlobTest> o, Connection c, String typeName) throws SQLException {
    OracleConnection connection = c.unwrap(OracleConnection.class);
    ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, connection);
    if (o == null) {
        return new ARRAY(arrayDescriptor, connection, new Object[0]);
    }
    List<Object> array = new ArrayList<Object>(o.size());
    for (TpBlobTest e : o) {
        array.add(TpBlobTestConverter.getStruct(e, connection));
    }
    return new ARRAY(arrayDescriptor, connection, array.toArray());
}

    public static TpBlobTest getObject(Struct struct) throws SQLException {
    if (struct == null || struct.getAttributes() == null || struct.getAttributes().length == 0) {
        return null;
    }
    TpBlobTest result = new TpBlobTest();
    Object[] attr = struct.getAttributes();
    if (attr[0] != null) {
        result.setThisIsABlob(((Blob)attr[0]).getBytes(1, (int)((Blob)attr[0]).length())); // THIS_IS_A_BLOB
    }
    if (attr[1] != null) {
        result.setBlobSize(((BigDecimal)attr[1]).intValue()); // BLOB_SIZE
    }
    return result;
}

    public static List<TpBlobTest> getObjectList(Array array) throws SQLException {
    List<TpBlobTest> result = new ArrayList<TpBlobTest>();
    ResultSet rs = null;
    try {
        rs = array.getResultSet();
        while (rs.next()) {
            result.add(TpBlobTestConverter.getObject((Struct) rs.getObject(2)));
        }
    } finally {
        if (rs != null) {
            rs.close();
        }
    }
    return result;
}

}
