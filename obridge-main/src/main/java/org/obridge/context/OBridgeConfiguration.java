package org.obridge.context;

/**
 *
 * @author fkarsany
 */
public class OBridgeConfiguration {

    private String jdbcUrl;
    private String sourceRoot;
    private String rootPackageName;
    private Packages packages;

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getSourceRoot() {
        return sourceRoot;
    }

    public void setSourceRoot(String sourceRoot) {
        this.sourceRoot = sourceRoot;
    }

    public String getRootPackageName() {
        return rootPackageName;
    }

    public void setRootPackageName(String rootPackageName) {
        this.rootPackageName = rootPackageName;
    }

    public Packages getPackages() {
        return packages;
    }

    public void setPackages(Packages packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        return "OBridgeConfiguration{" +
                "jdbcUrl='" + jdbcUrl + '\'' +
                ", sourceRoot='" + sourceRoot + '\'' +
                ", rootPackageName='" + rootPackageName + '\'' +
                ", packages=" + packages +
                '}';
    }
}
