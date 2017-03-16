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
public final class SampleTypeThreeConverter {

    public static final String TYPE_NAME = "SAMPLE_TYPE_THREE";

    private SampleTypeThreeConverter() {
    }

    public static Struct getStruct(SampleTypeThree o, Connection connection) throws SQLException {
    if (o == null) {
        return null;
    }
    List<Object> struct = new ArrayList<Object>();
    struct.add(0, o.getField1()); // FIELD1
    struct.add(1, o.getField2()); // FIELD2
    return connection.createStruct(TYPE_NAME, struct.toArray());
}

    public static Array getListArray(List<SampleTypeThree> o, Connection c, String typeName) throws SQLException {
    OracleConnection connection = c.unwrap(OracleConnection.class);
    ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, connection);
    if (o == null) {
        return new ARRAY(arrayDescriptor, connection, new Object[0]);
    }
    List<Object> array = new ArrayList<Object>(o.size());
    for (SampleTypeThree e : o) {
        array.add(SampleTypeThreeConverter.getStruct(e, connection));
    }
    return new ARRAY(arrayDescriptor, connection, array.toArray());
}

    public static SampleTypeThree getObject(Struct struct) throws SQLException {
    if (struct == null || struct.getAttributes() == null || struct.getAttributes().length == 0) {
        return null;
    }
    SampleTypeThree result = new SampleTypeThree();
    Object[] attr = struct.getAttributes();
    if (attr[0] != null) {
        result.setField1(((BigDecimal)attr[0]).intValue()); // FIELD1
    }
    if (attr[1] != null) {
        result.setField2((String)attr[1]); // FIELD2
    }
    return result;
}

    public static List<SampleTypeThree> getObjectList(Array array) throws SQLException {
    if (array == null) {
        return null;
    }
    List<SampleTypeThree> result = new ArrayList<SampleTypeThree>();
    ResultSet rs = null;
    try {
        rs = array.getResultSet();
        while (rs.next()) {
            result.add(SampleTypeThreeConverter.getObject((Struct) rs.getObject(2)));
        }
    } finally {
        if (rs != null) {
            rs.close();
        }
    }
    return result;
}

}
