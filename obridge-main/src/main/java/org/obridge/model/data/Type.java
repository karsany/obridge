package org.obridge.model.data;


import org.obridge.util.StringHelper;

import java.util.List;

/**
 * Created by fkarsany on 2015.01.11..
 */
public class Type {

    private String typeName;
    private List<TypeAttribute> attributeList;
    private String converterPackageName;
    private String objectPackage;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<TypeAttribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<TypeAttribute> attributeList) {
        this.attributeList = attributeList;
    }

    public String getJavaClassName() {
        return StringHelper.toCamelCase(typeName);
    }

    public String getConverterPackageName() {
        return converterPackageName;
    }

    public void setConverterPackageName(String converterPackageName) {
        this.converterPackageName = converterPackageName;
    }

    public String getObjectPackage() {
        return objectPackage;
    }

    public void setObjectPackage(String objectPackage) {
        this.objectPackage = objectPackage;
    }
}
