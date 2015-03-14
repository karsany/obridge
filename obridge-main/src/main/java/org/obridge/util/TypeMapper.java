package org.obridge.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fkarsany on 2014.12.31..
 */
public class TypeMapper {

    public static final String ORACLE_CHAR = "CHAR";
    public static final String ORACLE_VARCHAR2 = "VARCHAR2";
    public static final String ORACLE_NCHAR = "NCHAR";
    public static final String ORACLE_NVARCHAR2 = "NVARCHAR2";
    public static final String ORACLE_CLOB = "CLOB";
    public static final String ORACLE_NUMBER = "NUMBER";
    public static final String ORACLE_BINARY_INTEGER = "BINARY_INTEGER";
    public static final String ORACLE_REF_CURSOR = "REF CURSOR";
    public static final String ORACLE_DATE = "DATE";
    public static final String ORACLE_TIMESTAMP = "TIMESTAMP";
    public static final String ORACLE_BOOLEAN = "PL/SQL BOOLEAN";

    public static final String JAVA_STRING = "String";
    public static final String JAVA_INTEGER = "Integer";
    public static final String JAVA_BIGDECIMAL = "BigDecimal";
    public static final String JAVA_RESULTSET = "ResultSet";
    public static final String JAVA_DATE = "Date";
    public static final String JAVA_TIMESTAMP = "Timestamp";
    public static final String JAVA_BOOLEAN = "Boolean";

    private static Map<String, String> mappingData = null;

    public TypeMapper() {
        if (mappingData == null) {
            initMap();
        }
    }

    private void initMap() {
        mappingData = new HashMap<String, String>();
        mappingData.put(ORACLE_CHAR, JAVA_STRING);
        mappingData.put(ORACLE_VARCHAR2, JAVA_STRING);
        mappingData.put(ORACLE_NCHAR, JAVA_STRING);
        mappingData.put(ORACLE_NVARCHAR2, JAVA_STRING);
        mappingData.put(ORACLE_CLOB, JAVA_STRING);
        mappingData.put(ORACLE_NUMBER, JAVA_BIGDECIMAL);
        mappingData.put(ORACLE_BINARY_INTEGER, JAVA_INTEGER);
        mappingData.put(ORACLE_REF_CURSOR, JAVA_RESULTSET);
        mappingData.put(ORACLE_DATE, JAVA_DATE);
        mappingData.put(ORACLE_TIMESTAMP, JAVA_TIMESTAMP);
        mappingData.put(ORACLE_BOOLEAN, JAVA_BOOLEAN);
    }

    public String getMappedType(String oracleTypeName, int scale) {

        // exception: if tpye is NUMBER with scale 0
        if (oracleTypeName.equals(ORACLE_NUMBER) && scale == 0) {
            return JAVA_INTEGER;
        }

        if (mappingData.containsKey(oracleTypeName)) {
            return mappingData.get(oracleTypeName);
        } else {
            return "Object";
        }
    }


}
