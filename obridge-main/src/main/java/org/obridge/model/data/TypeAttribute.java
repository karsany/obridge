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

    public String getCollectionBaseType() {
        return collectionBaseType;
    }

    public void setCollectionBaseType(String collectionBaseType) {
        this.collectionBaseType = collectionBaseType;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public int getMultiType() {
        return multiType;
    }

    public void setMultiType(int multiType) {
        this.multiType = multiType;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrTypeName() {
        return attrTypeName;
    }

    public void setAttrTypeName(String attrTypeName) {
        this.attrTypeName = attrTypeName;
    }

    public int getAttrNo() {
        return attrNo;
    }

    public void setAttrNo(int attrNo) {
        this.attrNo = attrNo;
    }

    public int getDataScale() {
        return dataScale;
    }

    public void setDataScale(int dataScale) {
        this.dataScale = dataScale;
    }

    public String getJavaDataType() {
        if (multiType == 1) {
            if ("COLLECTION".equals(typeCode)) {
                return "List<" + getJavaCollectionBaseTypeNameBig() + ">";
            } else {
                return StringHelper.toCamelCase(attrTypeName);
            }
        } else {
            return new TypeMapper().getMappedType(this.attrTypeName, this.dataScale);
        }

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
        if ("COLLECTION".equals(typeCode)) {
            return String.format("struct.add(%d, %sConverter.getListArray(o.get%s(), connection)); // %s", getAttrNoIndex(), getJavaCollectionBaseTypeNameBig(), getJavaPropertyNameBig(), attrName);
        } else if ("OBJECT".equals(typeCode)) {
            return String.format("struct.add(%d, %sConverter.getStruct(o.get%s(), connection)); // %s", getAttrNoIndex(), getJavaDataType(), getJavaPropertyNameBig(), attrName);
//        } else if ("DATE".equals(attrTypeName)) {
//            return String.format("struct.add(%d, new Date(o.get%s())); // %s", getAttrNoIndex(), getJavaPropertyNameBig(), attrName);
//        } else if ("TIMESTAMP".equals(attrTypeName)) {
//            return String.format("struct.add(%d, new Timestamp(o.get%s())); // %s", getAttrNoIndex(), getJavaPropertyNameBig(), attrName);
        } else if ("CLOB".equals(attrTypeName)) {
            return String.format("Clob cl%d = connection.createClob();\n" +
                    "        cl%d.setString(1, o.get%s());\n" +
                    "        struct.add(%d, cl%d); // %s", getAttrNoIndex(), getAttrNoIndex(), getJavaPropertyNameBig(), getAttrNoIndex(), getAttrNoIndex(), attrName);
        } else {
            return String.format("struct.add(%d, o.get%s()); // %s", getAttrNoIndex(), getJavaPropertyNameBig(), attrName);
        }

    }

    public String getObjectAdder() {
        if ("COLLECTION".equals(typeCode)) {
            return String.format("result.set%s(%sConverter.getObjectList((Array)attr[%d])); // %s", getJavaPropertyNameBig(), getJavaCollectionBaseTypeNameBig(), getAttrNoIndex(), attrName);
        } else if ("OBJECT".equals(typeCode)) {
            return String.format("result.set%s(%sConverter.getObject((Struct)attr[%d])); // %s", getJavaPropertyNameBig(), getJavaDataType(), getAttrNoIndex(), attrName);
        } else if ("DATE".equals(attrTypeName)) {
            return String.format("result.set%s(new Date(((Timestamp)attr[%d]).getTime())); // %s", getJavaPropertyNameBig(), getAttrNoIndex(), attrName);
        } else if ("TIMESTAMP".equals(attrTypeName)) {
            return String.format("result.set%s((Timestamp)attr[%d]); // %s", getJavaPropertyNameBig(), getAttrNoIndex(), attrName);
        } else if ("CLOB".equals(attrTypeName)) {
            return String.format("result.set%s(((Clob)attr[%d]).getSubString(1, (int)((Clob)attr[%d]).length())); // %s", getJavaPropertyNameBig(), getAttrNoIndex(), getAttrNoIndex(), attrName);
        } else if ("Integer".equals(getJavaDataType())) {
            return String.format("result.set%s(((BigDecimal)attr[%d]).intValue()); // %s", getJavaPropertyNameBig(), getAttrNoIndex(), attrName);
        } else {
            return String.format("result.set%s((%s)attr[%d]); // %s", getJavaPropertyNameBig(), getJavaDataType(), getAttrNoIndex(), attrName);
        }

    }

    @Override
    public String toString() {
        return "TypeAttribute{" +
                "attrName='" + attrName + '\'' +
                ", attrTypeName='" + attrTypeName + '\'' +
                ", attrNo=" + attrNo +
                ", dataScale=" + dataScale +
                ", multiType=" + multiType +
                ", typeCode='" + typeCode + '\'' +
                ", collectionBaseType='" + collectionBaseType + '\'' +
                '}';
    }
}
