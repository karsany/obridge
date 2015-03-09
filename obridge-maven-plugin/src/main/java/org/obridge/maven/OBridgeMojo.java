package org.obridge.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.obridge.OBridge;
import org.obridge.context.OBridgeConfiguration;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Mojo(name = "generate", requiresProject = true)
public class OBridgeMojo extends AbstractMojo {

    @Parameter(defaultValue = "${basedir}\\src\\main\\java")
    private String baseDir;

    @Parameter(defaultValue = "${project.groupId}")
    private String groupId;

    @Parameter(property = "obridge.configuration", defaultValue = "${basedir}\\obridge.xml")
    private File configurationFile;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info(this.toString());

        try {
            OBridge o = new OBridge();
            OBridgeConfiguration config = o.loadConfiguration(configurationFile);

            if (config.getSourceRoot() == null || config.getSourceRoot().equals("")) {
                config.setSourceRoot(baseDir);
            }

            if (config.getRootPackageName() == null || config.getRootPackageName().equals("")) {
                config.setRootPackageName(groupId);
            }

            getLog().info(config.toString());

            o.generate(config);

        } catch (IOException e) {
            getLog().error(e);
        } catch (SQLException e) {
            getLog().error(e);
        } catch (PropertyVetoException e) {
            getLog().error(e);
        }

    }

    @Override
    public String toString() {
        return "OBridgeMojo{"
                + "baseDir='" + baseDir + '\''
                + ", configurationFile=" + configurationFile
                + '}';
    }
}
