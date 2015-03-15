package org.obridge.model.data;

import org.obridge.mappers.builders.ParameterGetSetRegisterBuilder;
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
    private String origTypeName;
    private int sequenceNumber;

    public ProcedureArgument(String argumentName, String dataType, String typeName, String defaulted, boolean inParam, boolean outParam, int sequence, String orig_type_name) {

        this.argumentName = argumentName;
        this.dataType = dataType;
        this.typeName = typeName;
        this.defaulted = defaulted;
        this.inParam = inParam;
        this.outParam = outParam;
        this.sequence = sequence;

        origTypeName = orig_type_name;
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
        if (isList()) {
            String mappedType = new TypeMapper().getMappedType(typeName, 0);
            if (mappedType.equals("Object")) {
                return "List<" + StringHelper.toCamelCase(typeName) + ">";
            } else {
                return "List<" + mappedType + ">";
            }
        } else {
            return StringHelper.toCamelCase(typeName);
        }
    }

    public boolean isList() {
        return dataType.equals("TABLE");
    }

    public boolean isPrimitiveList() {
        return isList() && !(new TypeMapper().getMappedType(typeName, 0).equals("Object"));
    }

    public String getUnderlyingTypeName() {
        if (isPrimitiveList()) {
            return new TypeMapper().getMappedType(typeName, 0);
        } else {
            return StringHelper.toCamelCase(typeName);
        }
    }

    public String getJavaDataType() {
        if (this.typeName != null) {
            return getJavaTypeName();
        } else {
            return new TypeMapper().getMappedType(this.dataType, 1);
        }
    }

    public String getOracleType() {
        if (dataType.equals("VARCHAR2")) {
            return "VARCHAR";
        } else if (dataType.equals("NVARCHAR2")) {
            return "NVARCHAR";
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

    public String getParamSet(int sequenceNumber) {
        return new ParameterGetSetRegisterBuilder(this).setParameter(sequenceNumber);
    }

    public String getRegOutput(int sequenceNumber) {
        if ("OBJECT".equals(dataType) || "TABLE".equals(dataType)) {
            return String.format("ocs.registerOutParameter(%d, Types.%s, \"%s\"); // %s", sequenceNumber, getOracleType(), origTypeName, argumentName);
        } else if (getOracleType().equals("BOOLEAN")) {
            return String.format("ocs.registerOutParameter(%d, %s); // %s", sequenceNumber, ("Types." + getOracleType()).replace("Types.CURSOR", "-10").replace("Types.BOOLEAN", "Types.INTEGER"), argumentName);
        } else {
            return String.format("ocs.registerOutParameter(%d, %s); // %s", sequenceNumber, ("Types." + getOracleType()).replace("Types.CURSOR", "-10").replace("Types.BOOLEAN", "Types.INTEGER"), argumentName);
        }

    }

    public String getParamGet(int sequenceNumber) {
        if ("OBJECT".equals(dataType)) {
            return String.format("ctx.set%s(%sConverter.getObject((Struct)ocs.getObject(%d))); // %s", getJavaPropertyNameBig(), getJavaDataType(), sequenceNumber, argumentName);
        } else if ("TABLE".equals(dataType)) {
            if (isPrimitiveList()) {
                return String.format("ctx.set%s(Arrays.asList(((%s[]) ((Array) ocs.getObject(%d)).getArray()))); // %s", getJavaPropertyNameBig(), getUnderlyingTypeName(), sequenceNumber, argumentName);
            }
            return String.format("ctx.set%s(%sConverter.getObjectList((Array)ocs.getObject(%d))); // %s", getJavaPropertyNameBig(), getUnderlyingTypeName(), sequenceNumber, argumentName);
        } else if ("Integer".equals(getJavaDataType())) {
            return String.format("ctx.set%s(ocs.getInt(%d)); // %s", getJavaPropertyNameBig(), sequenceNumber, argumentName);
        } else if ("BOOLEAN".equals(getOracleType())) {
            return String.format("ctx.set%s(null == ocs.getBigDecimal(%d) ? null : BigDecimal.ONE.equals(ocs.getBigDecimal(%d)) ? true : BigDecimal.ZERO.equals(ocs.getBigDecimal(%d)) ? false : null); // %s", getJavaPropertyNameBig(), sequenceNumber, sequenceNumber, sequenceNumber, argumentName);
        } else if ("ResultSet".equals(getJavaDataType())) {
            return String.format("ctx.set%s(ocs.getCursor(%d)); // %s", getJavaPropertyNameBig(), sequenceNumber, argumentName);
        } else {
            return String.format("ctx.set%s(ocs.get%s(%d)); // %s", getJavaPropertyNameBig(), getJavaDataType(), sequenceNumber, argumentName);
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
                + "}\n";
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getOrigTypeName() {
        return origTypeName;
    }

    public void setOrigTypeName(String origTypeName) {
        this.origTypeName = origTypeName;
    }
}
