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
public final class SampleTypeOneConverter {

    public static final String TYPE_NAME = "SAMPLE_TYPE_ONE";

    private SampleTypeOneConverter() {
    }

    public static Struct getStruct(SampleTypeOne o, Connection connection) throws SQLException {
    if (o == null) {
        return null;
    }
    List<Object> struct = new ArrayList<Object>();
    struct.add(0, o.getAttrVarchar()); // ATTR_VARCHAR
    Clob cl1 = connection.createClob();
    cl1.setString(1, o.getAttrClob());
    struct.add(1, cl1); // ATTR_CLOB
    struct.add(2, o.getAttrInt()); // ATTR_INT
    struct.add(3, o.getAttrBigdec1()); // ATTR_BIGDEC_1
    struct.add(4, o.getAttrBigdec2()); // ATTR_BIGDEC_2
    struct.add(5, o.getDateA()); // DATE_A
    struct.add(6, o.getTimestB()); // TIMEST_B
    struct.add(7, o.getTimestC()); // TIMEST_C
    struct.add(8, o.getRawCol()); // RAW_COL
    return connection.createStruct(TYPE_NAME, struct.toArray());
}

    public static Array getListArray(List<SampleTypeOne> o, Connection c, String typeName) throws SQLException {
    OracleConnection connection = c.unwrap(OracleConnection.class);
    ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, connection);
    if (o == null) {
        return new ARRAY(arrayDescriptor, connection, new Object[0]);
    }
    List<Object> array = new ArrayList<Object>(o.size());
    for (SampleTypeOne e : o) {
        array.add(SampleTypeOneConverter.getStruct(e, connection));
    }
    return new ARRAY(arrayDescriptor, connection, array.toArray());
}

    public static SampleTypeOne getObject(Struct struct) throws SQLException {
    if (struct == null || struct.getAttributes() == null || struct.getAttributes().length == 0) {
        return null;
    }
    SampleTypeOne result = new SampleTypeOne();
    Object[] attr = struct.getAttributes();
    if (attr[0] != null) {
        result.setAttrVarchar((String)attr[0]); // ATTR_VARCHAR
    }
    if (attr[1] != null) {
        result.setAttrClob(((Clob)attr[1]).getSubString(1, (int)((Clob)attr[1]).length())); // ATTR_CLOB
    }
    if (attr[2] != null) {
        result.setAttrInt(((BigDecimal)attr[2]).intValue()); // ATTR_INT
    }
    if (attr[3] != null) {
        result.setAttrBigdec1((BigDecimal)attr[3]); // ATTR_BIGDEC_1
    }
    if (attr[4] != null) {
        result.setAttrBigdec2((BigDecimal)attr[4]); // ATTR_BIGDEC_2
    }
    if (attr[5] != null) {
        result.setDateA(new Date(((Timestamp)attr[5]).getTime())); // DATE_A
    }
    if (attr[6] != null) {
        result.setTimestB((Timestamp)attr[6]); // TIMEST_B
    }
    if (attr[7] != null) {
        result.setTimestC((Timestamp)attr[7]); // TIMEST_C
    }
    if (attr[8] != null) {
        result.setRawCol((byte[])attr[8]); // RAW_COL
    }
    return result;
}

    public static List<SampleTypeOne> getObjectList(Array array) throws SQLException {
    if (array == null) {
        return null;
    }
    List<SampleTypeOne> result = new ArrayList<SampleTypeOne>();
    ResultSet rs = null;
    try {
        rs = array.getResultSet();
        while (rs.next()) {
            result.add(SampleTypeOneConverter.getObject((Struct) rs.getObject(2)));
        }
    } finally {
        if (rs != null) {
            rs.close();
        }
    }
    return result;
}

}
