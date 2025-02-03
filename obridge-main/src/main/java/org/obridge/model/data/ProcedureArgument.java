/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Ferenc Karsany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package org.obridge.model.data;

import org.obridge.mappers.builders.ParameterGetSetRegisterBuilder;
import org.obridge.util.StringHelper;
import org.obridge.util.TypeMapper;

/**
 * User: fkarsany Date: 2013.11.18.
 */
public class ProcedureArgument {

    public static final String  TABLE_DATATYPE_NAME = "TABLE";
    private             String  argumentName;
    private             String  dataType;
    private             String  typeName;
    private             boolean inParam;
    private             boolean outParam;
    private             String  origTypeName;
    private             int     sequenceNumber;

    public ProcedureArgument(String argumentName, String dataType, String typeName, boolean inParam, boolean outParam, String origTypeName) {
        this.argumentName = argumentName;
        this.dataType = dataType;
        this.typeName = typeName;
        this.inParam = inParam;
        this.outParam = outParam;
        this.origTypeName = origTypeName;
    }


    public boolean isInParam() {
        return inParam;
    }


    public boolean isOutParam() {
        return outParam;
    }


    public String getArgumentName() {
        return argumentName;
    }


    public String getDataType() {
        return dataType;
    }


    public String getJavaPropertyName() {

        String r;

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
            String mappedType = new TypeMapper().getJavaType(typeName, 0);
            if ("Object".equals(mappedType)) {
                return "List<" + StringHelper.toCamelCase(typeName) + ">";
            } else {
                return "List<" + mappedType + ">";
            }
        } else {
            return StringHelper.toCamelCase(typeName);
        }
    }

    public boolean isList() {
        return TABLE_DATATYPE_NAME.equals(dataType);
    }

    public boolean isPrimitiveList() {
        return isList() && !("Object".equals(new TypeMapper().getJavaType(typeName, 0)));
    }

    public String getUnderlyingTypeName() {
        if (isPrimitiveList()) {
            return new TypeMapper().getJavaType(typeName, 0);
        } else {
            return StringHelper.toCamelCase(typeName);
        }
    }

    public String getJavaDataType() {
        if (this.typeName != null) {
            return getJavaTypeName();
        } else {
            return new TypeMapper().getJavaType(this.dataType, 1);
        }
    }

    public String getJDBCType() {
        return new TypeMapper().getJDBCType(dataType);
    }


    public boolean isJDBCTypeBoolean() {
        return "BOOLEAN".equals(getJDBCType());
    }

    public String getParamSet(int sequenceNumber) {
        return new ParameterGetSetRegisterBuilder(this).setParameter(sequenceNumber);
    }

    public String getRegOutput(int sequenceNumber) {
        if ("OBJECT".equals(dataType) || TABLE_DATATYPE_NAME.equals(dataType)) {
            return String.format("ocs.registerOutParameter(%d, Types.%s, \"%s\"); // %s", sequenceNumber, getJDBCType(), origTypeName, argumentName);
        } else {
            return String.format("ocs.registerOutParameter(%d, %s); // %s", sequenceNumber, ("Types." + getJDBCType()).replace("Types.CURSOR", "-10").replace("Types.BOOLEAN", "Types.INTEGER"), argumentName);
        }

    }

    public String getParamGet(int sequenceNumber) {
        if ("OBJECT".equals(dataType)) {
            return String.format("ctx.set%s(%sConverter.getObject((Struct)ocs.getObject(%d))); // %s", getJavaPropertyNameBig(), getJavaDataType(), sequenceNumber, argumentName);
        } else if (TABLE_DATATYPE_NAME.equals(dataType)) {
            if (isPrimitiveList()) {
                return String.format("ctx.set%s(Arrays.asList((%s[]) ((Array) ocs.getObject(%d)).getArray())); // %s", getJavaPropertyNameBig(), getUnderlyingTypeName(), sequenceNumber, argumentName);
            }
            return String.format("ctx.set%s(%sConverter.getObjectList((Array)ocs.getObject(%d))); // %s", getJavaPropertyNameBig(), getUnderlyingTypeName(), sequenceNumber, argumentName);
        } else if ("Integer".equals(getJavaDataType())) {
            return String.format("ctx.set%s(ocs.getInt(%d)); // %s", getJavaPropertyNameBig(), sequenceNumber, argumentName);
        } else if ("BOOLEAN".equals(getJDBCType())) {
            return String.format("ctx.set%s(null == ocs.getBigDecimal(%d) ? null : BigDecimal.ONE.equals(ocs.getBigDecimal(%d)) ? true : BigDecimal.ZERO.equals(ocs.getBigDecimal(%d)) ? false : null); // %s", getJavaPropertyNameBig(), sequenceNumber, sequenceNumber, sequenceNumber, argumentName);
        } else if ("ResultSet".equals(getJavaDataType())) {
            return String.format("ctx.set%s((ResultSet)ocs.getObject(%d)); // %s", getJavaPropertyNameBig(), sequenceNumber, argumentName);
        } else if (TypeMapper.JAVA_BYTEARRAY.equals(getJavaDataType())) {
            return String.format("ctx.set%s(ocs.getBytes(%d)); // %s", getJavaPropertyNameBig(), sequenceNumber, argumentName);
        } else if (TypeMapper.JAVA_DATE.equals(getJavaDataType())) {
            return String.format("ctx.set%s(ocs.getDate(%d).toLocalDate()); // %s", getJavaPropertyNameBig(), sequenceNumber, argumentName);
        } else {
            return String.format("ctx.set%s(ocs.get%s(%d)); // %s", getJavaPropertyNameBig(), getJavaDataType(), sequenceNumber, argumentName);
        }

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


    public boolean isOutputBooleanArgument() {
        return getArgumentName() != null && isJDBCTypeBoolean() && isOutParam();
    }
}
