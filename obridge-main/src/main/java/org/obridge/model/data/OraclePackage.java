package org.obridge.model.data;

import org.obridge.util.StringHelper;

import java.util.List;

/**
 * Created by fkarsany on 2015.01.05..
 */
public class OraclePackage {

    private String name;
    private List<Procedure> procedureList;
    private String javaPackageName;
    private String contextPackage;
    private String converterPackage;
    private String objectPackage;

    public List<Procedure> getProcedureList() {
        return procedureList;
    }

    public void setProcedureList(List<Procedure> procedureList) {
        this.procedureList = procedureList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJavaClassName() {
        return StringHelper.toCamelCase(name);
    }

    public String getJavaPackageName() {
        return javaPackageName;
    }

    public void setJavaPackageName(String javaPackageName) {
        this.javaPackageName = javaPackageName;
    }

    public String getContextPackage() {
        return contextPackage;
    }

    public void setContextPackage(String contextPackage) {
        this.contextPackage = contextPackage;
    }

    public String getConverterPackage() {
        return converterPackage;
    }

    public void setConverterPackage(String converterPackage) {
        this.converterPackage = converterPackage;
    }

    public String getObjectPackage() {
        return objectPackage;
    }

    public void setObjectPackage(String objectPackage) {
        this.objectPackage = objectPackage;
    }
}
