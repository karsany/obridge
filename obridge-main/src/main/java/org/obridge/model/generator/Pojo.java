package org.obridge.model.generator;

import java.util.List;

/**
 * Created by fkarsany on 2015.01.03..
 */
public class Pojo {

    private String packageName;
    private String className;
    private List<PojoField> fields;
    private String comment;
    private List<String> imports;
    private List<String> otherCode;
    private String generatorName;

    public List<String> getOtherCode() {
        return otherCode;
    }

    public void setOtherCode(List<String> otherCode) {
        this.otherCode = otherCode;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<PojoField> getFields() {
        return fields;
    }

    public void setFields(List<PojoField> fields) {
        this.fields = fields;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", fields=" + fields +
                ", comment='" + comment + '\'' +
                ", imports=" + imports +
                ", otherCode=" + otherCode +
                '}';
    }

    public void setGeneratorName(String generatorName) {
        this.generatorName = generatorName;
    }

    public String getGeneratorName() {
        return generatorName;
    }
}
