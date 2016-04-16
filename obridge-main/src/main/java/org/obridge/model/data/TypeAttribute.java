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


import org.obridge.util.StringHelper;
import org.obridge.util.TypeMapper;

public class TypeAttribute {

    private String attrName;
    private String attrTypeName;
    private int attrNo;
    private int dataScale;
    private int multiType;
    private String typeCode;
    private String collectionBaseType;

    public TypeAttribute(String attrName, String attrTypeName, int attrNo, int dataScale, int multiType, String typeCode, String collectionBaseType) {
        this.attrName = attrName;
        this.attrTypeName = attrTypeName;
        this.attrNo = attrNo;
        this.dataScale = dataScale;
        this.multiType = multiType;
        this.typeCode = typeCode;
        this.collectionBaseType = collectionBaseType;


    }

    public String getAttrName() {
        return attrName;
    }

    public String getAttrTypeName() {
        return attrTypeName;
    }

    public String getJavaDataType() {
        if (multiType == 1) {
            if (TypeMapper.ORACLE_COLLECTION.equals(typeCode)) {
                if (isPrimitiveList()) {
                    return "List<" + new TypeMapper().getJavaType(collectionBaseType, dataScale) + ">";
                } else {
                    return "List<" + getJavaCollectionBaseTypeNameBig() + ">";
                }
            } else {
                return StringHelper.toCamelCase(attrTypeName);
            }
        } else {
            return new TypeMapper().getJavaType(this.attrTypeName, this.dataScale);
        }

    }

    public boolean isPrimitiveList() {
        return !("Object".equals(new TypeMapper().getJavaType(collectionBaseType, 0)));
    }


    public String getJavaPropertyName() {
        return StringHelper.toCamelCaseSmallBegin(this.attrName);
    }

    public String getJavaPropertyNameBig() {
        return StringHelper.toCamelCase(this.attrName);
    }

    public String getJavaCollectionBaseTypeNameBig() {
        return StringHelper.toCamelCase(this.collectionBaseType);
    }

    public int getAttrNoIndex() {
        return this.attrNo - 1;
    }

    public String getStructAdder() {
        if (TypeMapper.ORACLE_COLLECTION.equals(typeCode) && !isPrimitiveList()) {
            return String.format("struct.add(%d, %sConverter.getListArray(o.get%s(), connection, \"%s\")); // %s", getAttrNoIndex(), getJavaCollectionBaseTypeNameBig(), getJavaPropertyNameBig(), getAttrTypeName(), attrName);
        } else if (TypeMapper.ORACLE_COLLECTION.equals(typeCode) && isPrimitiveList()) {
            return String.format("struct.add(%d, PrimitiveTypeConverter.getListArray(o.get%s(), connection, \"%s\")); // %s", getAttrNoIndex(), getJavaPropertyNameBig(), getAttrTypeName(), attrName);
        } else if (TypeMapper.ORACLE_OBJECT.equals(typeCode)) {
            return String.format("struct.add(%d, %sConverter.getStruct(o.get%s(), connection)); // %s", getAttrNoIndex(), getJavaDataType(), getJavaPropertyNameBig(), attrName);
        } else if (TypeMapper.ORACLE_CLOB.equals(attrTypeName)) {
            return String.format("Clob cl%d = connection.createClob();\n" +
                    "        cl%d.setString(1, o.get%s());\n" +
                    "        struct.add(%d, cl%d); // %s", getAttrNoIndex(), getAttrNoIndex(), getJavaPropertyNameBig(), getAttrNoIndex(), getAttrNoIndex(), attrName);
        } else {
            return String.format("struct.add(%d, o.get%s()); // %s", getAttrNoIndex(), getJavaPropertyNameBig(), attrName);
        }

    }

    public String getObjectAdder() {
        if (TypeMapper.ORACLE_COLLECTION.equals(typeCode) && !isPrimitiveList()) {
            return String.format("result.set%s(%sConverter.getObjectList((Array)attr[%d])); // %s", getJavaPropertyNameBig(), getJavaCollectionBaseTypeNameBig(), getAttrNoIndex(), attrName);
        } else if (TypeMapper.ORACLE_COLLECTION.equals(typeCode) && isPrimitiveList()) {
            return String.format("result.set%s(PrimitiveTypeConverter.asList((Array) attr[%d], %s.class)); // %s", getJavaPropertyNameBig(), getAttrNoIndex(), getUnderlyingJavaTypeName(), attrName);
        } else if (TypeMapper.ORACLE_OBJECT.equals(typeCode)) {
            return String.format("result.set%s(%sConverter.getObject((Struct)attr[%d])); // %s", getJavaPropertyNameBig(), getJavaDataType(), getAttrNoIndex(), attrName);
        } else if (TypeMapper.ORACLE_DATE.equals(attrTypeName)) {
            return String.format("result.set%s(new Date(((Timestamp)attr[%d]).getTime())); // %s", getJavaPropertyNameBig(), getAttrNoIndex(), attrName);
        } else if (TypeMapper.ORACLE_TIMESTAMP.equals(attrTypeName)) {
            return String.format("result.set%s((Timestamp)attr[%d]); // %s", getJavaPropertyNameBig(), getAttrNoIndex(), attrName);
        } else if (TypeMapper.ORACLE_CLOB.equals(attrTypeName)) {
            return String.format("result.set%s(((Clob)attr[%d]).getSubString(1, (int)((Clob)attr[%d]).length())); // %s", getJavaPropertyNameBig(), getAttrNoIndex(), getAttrNoIndex(), attrName);
        } else if (TypeMapper.ORACLE_INTEGER.equals(attrTypeName)) {
            return String.format("result.set%s(((BigDecimal)attr[%d]).intValue()); // %s", getJavaPropertyNameBig(), getAttrNoIndex(), attrName);
        } else if (TypeMapper.JAVA_INTEGER.equals(getJavaDataType())) {
            return String.format("result.set%s(((BigDecimal)attr[%d]).intValue()); // %s", getJavaPropertyNameBig(), getAttrNoIndex(), attrName);
        } else {
            return String.format("result.set%s((%s)attr[%d]); // %s", getJavaPropertyNameBig(), getJavaDataType(), getAttrNoIndex(), attrName);
        }

    }

    public String getUnderlyingJavaTypeName() {
        if (multiType == 1) {
            if (TypeMapper.ORACLE_COLLECTION.equals(typeCode)) {
                if (isPrimitiveList()) {
                    return new TypeMapper().getJavaType(collectionBaseType, dataScale);
                } else {
                    return getJavaCollectionBaseTypeNameBig();
                }
            } else {
                return StringHelper.toCamelCase(attrTypeName);
            }
        } else {
            return new TypeMapper().getJavaType(this.attrTypeName, this.dataScale);
        }

    }
}
