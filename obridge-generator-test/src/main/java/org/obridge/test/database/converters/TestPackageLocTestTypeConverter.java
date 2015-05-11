package org.obridge.test.database.converters;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import org.obridge.test.database.objects.TestPackageLocTestType;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public final class TestPackageLocTestTypeConverter {

    private final static String TYPE_NAME = "OBRIDGE.TEST_PACKAGE.LOC_TEST_TYPE";

    private TestPackageLocTestTypeConverter() {
    }

    public static Struct getStruct(TestPackageLocTestType o, Connection connection) throws SQLException {
        if (o == null) {
            return null;
        }
        List<Object> struct = new ArrayList<Object>();
        struct.add(0, o.getId()); // ID
        struct.add(1, o.getName()); // NAME
        return connection.createStruct(TYPE_NAME, struct.toArray());
    }

    public static Array getListArray(List<TestPackageLocTestType> o, Connection c, String typeName) throws SQLException {
        OracleConnection connection = c.unwrap(OracleConnection.class);
        ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, connection);
        if (o == null) {
            return new ARRAY(arrayDescriptor, connection, new Object[0]);
        }
        List<Object> array = new ArrayList<Object>(o.size());
        for (TestPackageLocTestType e : o) {
            array.add(TestPackageLocTestTypeConverter.getStruct(e, connection));
        }
        return new ARRAY(arrayDescriptor, connection, array.toArray());
    }

    public static TestPackageLocTestType getObject(Struct struct) throws SQLException {
        if (struct == null || struct.getAttributes() == null || struct.getAttributes().length == 0) {
            return null;
        }
        TestPackageLocTestType result = new TestPackageLocTestType();
        Object[] attr = struct.getAttributes();
        if (attr[0] != null)
            result.setId(((BigDecimal) attr[0])); // ID
        if (attr[1] != null)
            result.setName(((String) attr[1])); // NAME
        return result;
    }

    public static List<TestPackageLocTestType> getObjectList(Array array) throws SQLException {
        List<TestPackageLocTestType> result = new ArrayList<TestPackageLocTestType>();
        ResultSet rs = null;
        try {
            rs = array.getResultSet();
            while (rs.next()) {
                result.add(TestPackageLocTestTypeConverter.getObject((Struct) rs.getObject(2)));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        return result;
    }

}
