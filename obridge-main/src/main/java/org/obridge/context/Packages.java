package org.obridge.context;

/**
 * @author fkarsany
 */
public class Packages {

    private String entityObjects = "objects";
    private String converterObjects = "converters";
    private String procedureContextObjects = "context";
    private String packageObjects = "packages";

    public String getEntityObjects() {
        return entityObjects;
    }

    public void setEntityObjects(String entityObjects) {
        this.entityObjects = entityObjects;
    }

    public String getConverterObjects() {
        return converterObjects;
    }

    public void setConverterObjects(String converterObjects) {
        this.converterObjects = converterObjects;
    }

    public String getProcedureContextObjects() {
        return procedureContextObjects;
    }

    public void setProcedureContextObjects(String procedureContextObjects) {
        this.procedureContextObjects = procedureContextObjects;
    }

    public String getPackageObjects() {
        return packageObjects;
    }

    public void setPackageObjects(String packageObjects) {
        this.packageObjects = packageObjects;
    }

    @Override
    public String toString() {
        return "Packages{" +
                "entityObjects='" + entityObjects + '\'' +
                ", converterObjects='" + converterObjects + '\'' +
                ", procedureContextObjects='" + procedureContextObjects + '\'' +
                ", packageObjects='" + packageObjects + '\'' +
                '}';
    }
}
