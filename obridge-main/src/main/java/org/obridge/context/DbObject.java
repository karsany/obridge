package org.obridge.context;

public class DbObject {

    private String owner;
    private String name;

    public DbObject(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public DbObject() {
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toConcatenated() {
        return this.owner + "." + this.name;
    }

    public String toSQL() {
        return "SELECT '" + this.owner + "', '" + this.name + "' FROM dual ";
    }

}
