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


import lombok.AllArgsConstructor;
import lombok.ToString;
import org.obridge.util.StringHelper;
import org.obridge.util.TypeMapper;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@AllArgsConstructor
@ToString
public class TypeAttribute {

    private final String attrName;
    private final String attrTypeName;
    private final String owner;
    private final int attrNo;
    private final int dataScale;
    private final int multiType;
    private final String typeCode;
    private final String collectionBaseType;


    public String getAttrName() {
        return attrName;
    }

    public String getAttrTypeName() {
        return attrTypeName;
    }

    public String getOwner() {
        return owner;
    }
    public String getTypeCode() {
        return typeCode;
    }
    public String getCollectionBaseType() {
        return collectionBaseType;
    }
    public int getMultiType() {
        return multiType;
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
                return StringHelper.toPascalCase(attrTypeName);
            }
        } else {
            return new TypeMapper().getJavaType(this.attrTypeName, this.dataScale);
        }

    }

    public boolean isPrimitiveList() {
        if(!Objects.isNull(collectionBaseType)) {
            return !("Object".equals(new TypeMapper().getJavaType(collectionBaseType, 0)));
        } else {
            return false;
        }
    }


    public String getJavaPropertyName() {
        return StringHelper.toCamelCase(this.attrName);
    }

    public String getJavaPropertyNameBig() {
        return StringHelper.toPascalCase(this.attrName);
    }

    public String getJavaCollectionBaseTypeNameBig() {
        return StringHelper.toPascalCase(this.collectionBaseType);
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
        } else if (TypeMapper.ORACLE_BLOB.equals(attrTypeName)) {
            return String.format("Blob bl%d = connection.createBlob();\n" +
                                 "        bl%d.setBytes(1, o.get%s());\n" +
                                 "        struct.add(%d, bl%d); // %s", getAttrNoIndex(), getAttrNoIndex(), getJavaPropertyNameBig(), getAttrNoIndex(), getAttrNoIndex(), attrName);
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
        } else if (TypeMapper.ORACLE_BLOB.equals(attrTypeName)) {
            return String.format("result.set%s(((Blob)attr[%d]).getBytes(1, (int)((Blob)attr[%d]).length())); // %s", getJavaPropertyNameBig(), getAttrNoIndex(), getAttrNoIndex(), attrName);
        } else if (TypeMapper.ORACLE_INTEGER.equals(attrTypeName) || TypeMapper.JAVA_INTEGER.equals(getJavaDataType())) {
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
                return StringHelper.toPascalCase(attrTypeName);
            }
        } else {
            return new TypeMapper().getJavaType(this.attrTypeName, this.dataScale);
        }

    }
}
