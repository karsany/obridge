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
