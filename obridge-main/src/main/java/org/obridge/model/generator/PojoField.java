package org.obridge.model.generator;

/**
 * Created by fkarsany on 2015.01.03..
 */
public class PojoField {

    private String fieldName;
    private String fieldType;
    private boolean readonly;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldNameInitCap() {
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public boolean isSettable() {
        return !isReadonly();
    }

    @Override
    public String toString() {
        return "PojoField{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", readonly=" + readonly +
                '}';
    }
}
