package org.obridge.test.database.converters;

import org.obridge.test.database.objects.*;
import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public final class SampleTypeTwoConverter {

    private final static String TYPE_NAME = "SAMPLE_TYPE_TWO";

    private SampleTypeTwoConverter() {
    }

    public static Struct getStruct(SampleTypeTwo o, Connection connection) throws SQLException {
        if (o == null) {
            return null;
        }
        List<Object> struct = new ArrayList<Object>();
        struct.add(0, o.getField1()); // FIELD1
        struct.add(1, SampleTypeOneConverter.getStruct(o.getField2(), connection)); // FIELD2
        struct.add(2, SampleTypeOneConverter.getListArray(o.getField3(), connection, "SAMPLE_TYPE_ONE_LIST")); // FIELD3
        return connection.createStruct(TYPE_NAME, struct.toArray());
    }

    public static Array getListArray(List<SampleTypeTwo> o, Connection c, String typeName) throws SQLException {
        OracleConnection connection = c.unwrap(OracleConnection.class);
        ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, connection);
        if (o == null) {
            return new ARRAY(arrayDescriptor, connection, new Object[0]);
        }
        List<Object> array = new ArrayList<Object>(o.size());
        for (SampleTypeTwo e : o) {
            array.add(SampleTypeTwoConverter.getStruct(e, connection));
        }
        return new ARRAY(arrayDescriptor, connection, array.toArray());
    }

    public static SampleTypeTwo getObject(Struct struct) throws SQLException {
        if (struct == null || struct.getAttributes() == null || struct.getAttributes().length == 0) {
            return null;
        }
        SampleTypeTwo result = new SampleTypeTwo();
        Object[] attr = struct.getAttributes();
        if (attr[0] != null)
            result.setField1((String)attr[0]); // FIELD1
        if (attr[1] != null)
            result.setField2(SampleTypeOneConverter.getObject((Struct)attr[1])); // FIELD2
        if (attr[2] != null)
            result.setField3(SampleTypeOneConverter.getObjectList((Array)attr[2])); // FIELD3
        return result;
    }

    public static List<SampleTypeTwo> getObjectList(Array array) throws SQLException {
        List<SampleTypeTwo> result = new ArrayList<SampleTypeTwo>();
        ResultSet rs = null;
        try {
            rs = array.getResultSet();
            while (rs.next()) {
                result.add(SampleTypeTwoConverter.getObject((Struct) rs.getObject(2)));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return result;
    }

}
