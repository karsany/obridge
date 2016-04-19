package org.obridge.test.database.converters;

import oracle.jdbc.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Generated("org.obridge.generators.ConverterObjectGenerator")
public final class PrimitiveTypeConverter {

    private PrimitiveTypeConverter() {
    }

    public static Array getListArray(List o, Connection c, String typeName) throws SQLException {
    OracleConnection connection = c.unwrap(OracleConnection.class);
    ArrayDescriptor arrayDescriptor = new ArrayDescriptor(typeName, connection);
    if (o == null) {
        return new ARRAY(arrayDescriptor, connection, new Object[0]);
    }
    List<Object> array = new ArrayList<Object>(o.size());
    for (Object e : o) {
        array.add(e);
    }
    return new ARRAY(arrayDescriptor, connection, array.toArray());
}

    public static <T> List<T> asList(Array array, Class<T> targetClass) throws SQLException {
    if (targetClass.equals(Integer.class)) {
        List<T> r = new ArrayList<T>();
        final BigDecimal[] baseArray = (BigDecimal[]) array.getArray();
        for (BigDecimal b : baseArray) {
            r.add((T) new Integer(b.intValue()));
        }
        return r;
    } else {
        return Arrays.asList((T[]) array.getArray());
    }
}

}
