package org.obridge.model.data;

import org.obridge.util.StringHelper;
import org.obridge.util.TypeMapper;

/**
 * User: fkarsany Date: 2013.11.18.
 */
public class ProcedureArgument {

    private String argumentName;
    private String dataType;
    private String typeName;
    private String defaulted;
    private boolean inParam;
    private boolean outParam;
    private int sequence;

    public ProcedureArgument(String argumentName, String dataType, String typeName, String defaulted, boolean inParam, boolean outParam, int sequence) {

        this.argumentName = argumentName;
        this.dataType = dataType;
        this.typeName = typeName;
        this.defaulted = defaulted;
        this.inParam = inParam;
        this.outParam = outParam;
        this.sequence = sequence;

    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public boolean isInParam() {
        return inParam;
    }

    public void setInParam(boolean inParam) {
        this.inParam = inParam;
    }

    public boolean isOutParam() {
        return outParam;
    }

    public void setOutParam(boolean outParam) {
        this.outParam = outParam;
    }

    public String getDefaulted() {
        return defaulted;
    }

    public void setDefaulted(String defaulted) {
        this.defaulted = defaulted;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public void setArgumentName(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getJavaPropertyName() {

        String r = null;

        if (this.argumentName == null) {
            return "functionReturn";
        } else if (this.argumentName.startsWith("P_")) {
            r = this.argumentName.substring(2);
        } else {
            r = this.argumentName;
        }

        if (Character.isDigit(r.charAt(0))) {
            r = "P_" + r;
        }

        return StringHelper.unJavaKeyword(StringHelper.toCamelCaseSmallBegin(r));

    }

    public String getJavaPropertyNameBig() {
        String javaPropName = this.getJavaPropertyName();
        return javaPropName.substring(0, 1).toUpperCase() + javaPropName.substring(1);
    }

    public String getJavaTypeName() {
        if (typeName.endsWith("_LIST")) {
            return "List<" + StringHelper.toCamelCase(typeName.substring(0, typeName.length() - 5)) + ">";
        } else {
            return StringHelper.toCamelCase(typeName);
        }
    }

    public boolean isList() {
        return typeName.endsWith("_LIST");
    }

    public String getUnderlyingTypeName() {
        if (typeName.endsWith("_LIST")) {
            return StringHelper.toCamelCase(typeName.substring(0, typeName.length() - 5));
        } else {
            return StringHelper.toCamelCase(typeName);
        }
    }

    public String getJavaDataType() {
        if (this.typeName != null) {
            return getJavaTypeName();
        } else {
            return new TypeMapper().getMappedType(this.dataType, 0);
        }
    }

    public String getOracleType() {
        if (dataType.equals("VARCHAR2")) {
            return "VARCHAR";
        } else if (dataType.equals("REF CURSOR")) {
            return "CURSOR";
        } else if (dataType.equals("BINARY_INTEGER")) {
            return "INTEGER";
        } else if (dataType.equals("OBJECT")) {
            return "STRUCT";
        } else if (dataType.equals("TABLE")) {
            return "ARRAY";
        } else if (dataType.equals("PL/SQL BOOLEAN")) {
            return "BOOLEAN";
        } else if (dataType.equals("NUMBER")) {
            return "NUMERIC";
        } else {
            return dataType;
        }
    }

    public String getInoutType() {
        return (inParam ? "IN" : "") + (outParam ? "OUT" : "");
    }

    public String getArgumentParameterName() {
        if (argumentName == null) {
            return null;
        }
        return argumentName + " => ?";
    }

    public String getParamSet() {
        if ("OBJECT".equals(dataType)) {
            return String.format("ocs.setObject(%d, %sConverter.getStruct(ctx.get%s(),connection)); // %s", sequence, getJavaDataType(), getJavaPropertyNameBig(), argumentName);
        } else if ("TABLE".equals(dataType)) {
            return String.format("ocs.setObject(%d, %sConverter.getListArray(ctx.get%s(), connection)); // %s", sequence, getUnderlyingTypeName(), getJavaPropertyNameBig(), argumentName);
        } else if ("Integer".equals(getJavaDataType())) {
            return String.format("ocs.setInt(%d, ctx.get%s()); // %s", sequence, getJavaPropertyNameBig(), argumentName);
        } else {
            return String.format("ocs.set%s(%d, ctx.get%s()); // %s", getJavaDataType(), sequence, getJavaPropertyNameBig(), argumentName);
        }
    }

    public String getRegOutput() {
        if ("OBJECT".equals(dataType) || "TABLE".equals(dataType)) {
            return String.format("ocs.registerOutParameter(%d, Types.%s, \"%s\"); // %s", sequence, getOracleType(), typeName, argumentName);
        } else {
            return String.format("ocs.registerOutParameter(%d, %s); // %s", sequence, ("Types." + getOracleType()).replace("Types.CURSOR", "-10"), argumentName);
        }

    }

    public String getParamGet() {
        if ("OBJECT".equals(dataType)) {
            return String.format("ctx.set%s(%sConverter.getObject((Struct)ocs.getObject(%d))); // %s", getJavaPropertyNameBig(), getJavaDataType(), sequence, argumentName);
        } else if ("TABLE".equals(dataType)) {
            return String.format("ctx.set%s(%sConverter.getObjectList((Array)ocs.getObject(%d))); // %s", getJavaPropertyNameBig(), getUnderlyingTypeName(), sequence, argumentName);
        } else if ("Integer".equals(getJavaDataType())) {
            return String.format("ctx.set%s(ocs.getInt(%d)); // %s", getJavaPropertyNameBig(), sequence, argumentName);
        } else if ("ResultSet".equals(getJavaDataType())) {
            return String.format("ctx.set%s(ocs.getCursor(%d)); // %s", getJavaPropertyNameBig(), sequence, argumentName);
        } else {
            return String.format("ctx.set%s(ocs.get%s(%d)); // %s", getJavaPropertyNameBig(), getJavaDataType(), sequence, argumentName);
        }

    }

    @Override
    public String toString() {
        return "ProcedureArgument{"
                + "argumentName='" + argumentName + '\''
                + ", dataType='" + dataType + '\''
                + ", typeName='" + typeName + '\''
                + ", defaulted='" + defaulted + '\''
                + ", inParam=" + inParam
                + ", outParam=" + outParam
                + ", sequence=" + sequence
                + '}';
    }
}
