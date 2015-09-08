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
    public static final String ORACLE_INTEGER = "INTEGER";
    public static final String ORACLE_REF_CURSOR = "REF CURSOR";
    public static final String ORACLE_DATE = "DATE";
    public static final String ORACLE_TIMESTAMP = "TIMESTAMP";
    public static final String ORACLE_BOOLEAN = "PL/SQL BOOLEAN";
    public static final String ORACLE_OBJECT = "OBJECT";
    public static final String ORACLE_TABLE = "TABLE";

    public static final String JAVA_STRING = "String";
    public static final String JAVA_INTEGER = "Integer";
    public static final String JAVA_BIGDECIMAL = "BigDecimal";
    public static final String JAVA_RESULTSET = "ResultSet";
    public static final String JAVA_DATE = "Date";
    public static final String JAVA_TIMESTAMP = "Timestamp";
    public static final String JAVA_BOOLEAN = "Boolean";

    public static final String JDBC_VARCHAR = "VARCHAR";
    public static final String JDBC_NVARCHAR = "NVARCHAR";
    public static final String JDBC_CURSOR = "CURSOR";
    public static final String JDBC_INTEGER = "INTEGER";
    public static final String JDBC_STRUCT = "STRUCT";
    public static final String JDBC_ARRAY = "ARRAY";
    public static final String JDBC_BOOLEAN = "BOOLEAN";
    public static final String JDBC_NUMERIC = "NUMERIC";

    private static Map<String, String> oracleToJavaMapping = null;
    private static Map<String, String> oracleToJDBCMapping = null;

    public TypeMapper() {
        if (oracleToJavaMapping == null) {
            initOracleToJavaMapping();
        }
        if (oracleToJDBCMapping == null) {
            initOracleToJDBCMapping();
        }
    }

    private void initOracleToJDBCMapping() {
        oracleToJDBCMapping = new HashMap<String, String>();
        oracleToJDBCMapping.put(ORACLE_VARCHAR2, JDBC_VARCHAR);
        oracleToJDBCMapping.put(ORACLE_NVARCHAR2, JDBC_NVARCHAR);
        oracleToJDBCMapping.put(ORACLE_REF_CURSOR, JDBC_CURSOR);
        oracleToJDBCMapping.put(ORACLE_BINARY_INTEGER, JDBC_INTEGER);
        oracleToJDBCMapping.put(ORACLE_OBJECT, JDBC_STRUCT);
        oracleToJDBCMapping.put(ORACLE_TABLE, JDBC_ARRAY);
        oracleToJDBCMapping.put(ORACLE_BOOLEAN, JDBC_BOOLEAN);
        oracleToJDBCMapping.put(ORACLE_NUMBER, JDBC_NUMERIC);
    }

    private void initOracleToJavaMapping() {
        oracleToJavaMapping = new HashMap<String, String>();
        oracleToJavaMapping.put(ORACLE_CHAR, JAVA_STRING);
        oracleToJavaMapping.put(ORACLE_VARCHAR2, JAVA_STRING);
        oracleToJavaMapping.put(ORACLE_NCHAR, JAVA_STRING);
        oracleToJavaMapping.put(ORACLE_NVARCHAR2, JAVA_STRING);
        oracleToJavaMapping.put(ORACLE_CLOB, JAVA_STRING);
        oracleToJavaMapping.put(ORACLE_NUMBER, JAVA_BIGDECIMAL);
        oracleToJavaMapping.put(ORACLE_BINARY_INTEGER, JAVA_INTEGER);
        oracleToJavaMapping.put(ORACLE_INTEGER, JAVA_INTEGER);
        oracleToJavaMapping.put(ORACLE_REF_CURSOR, JAVA_RESULTSET);
        oracleToJavaMapping.put(ORACLE_DATE, JAVA_DATE);
        oracleToJavaMapping.put(ORACLE_TIMESTAMP, JAVA_TIMESTAMP);
        oracleToJavaMapping.put(ORACLE_BOOLEAN, JAVA_BOOLEAN);
    }


    public String getJavaType(String oracleTypeName, int scale) {

        // exception: if tpye is NUMBER with scale 0
        if (oracleTypeName.equals(ORACLE_NUMBER) && scale == 0) {
            return JAVA_INTEGER;
        }

        if (oracleToJavaMapping.containsKey(oracleTypeName)) {
            return oracleToJavaMapping.get(oracleTypeName);
        } else {
            return "Object";
        }
    }

    public String getJDBCType(String oracleTypeName) {
        if (oracleToJDBCMapping.containsKey(oracleTypeName)) {
            return oracleToJDBCMapping.get(oracleTypeName);
        } else {
            return oracleTypeName;
        }
    }

}
