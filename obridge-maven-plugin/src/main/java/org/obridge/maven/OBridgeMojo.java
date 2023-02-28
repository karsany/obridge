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

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.obridge.OBridge;
import org.obridge.maven.model.Include;
import org.obridge.maven.model.Packages;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.PathResource;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.IntStream;

@Mojo(name = "obridge", requiresProject = true, defaultPhase = LifecyclePhase.GENERATE_SOURCES, requiresDependencyResolution = ResolutionScope.COMPILE)
public class OBridgeMojo extends AbstractMojo {

    /**
     * The path of the file to operate on.
     */
    @Parameter(property = "obridge.plugin.path", defaultValue = "src/main/resources/application.yml")
    private String configPath = "src/main/resources/application.yml";

    @Parameter(property = "obridge.jdbcUrl")
    private String jdbcUrl;
    @Parameter(property = "obridge.generateNestedTypes", defaultValue = "false")
    private boolean generateNestedTypes = false;
    @Parameter(property = "obridge.packages")
    private Packages packages;
    @Parameter(property = "obridge.sourceRoot")
    private String sourceRoot;
    @Parameter(property = "obridge.rootPackageName")
    private String rootPackageName;
    @Parameter(property = "obridge.includes")
    private List<Include> includes;

    @Parameter(property = "project", required = true, readonly = true)
    protected MavenProject project;

    /**
     * <p>Getter for the field <code>environment</code>.</p>
     *
     * @return a {@link org.springframework.core.env.Environment} object
     */
    private Environment environment;

    @Override
    public void execute() {
        getLog().info(this.toString());
        final Properties applicationStartupProperties = new Properties();

        try {
            Properties configProperties;
            String extension = FilenameUtils.getExtension(configPath);
            PathResource resource = new PathResource(Path.of(configPath).toAbsolutePath());
            if (resource.exists()) {
                getLog().debug("Resource exists, trying to load.");
                switch (extension) {
                    case "yml":
                    case "yaml":
                        getLog().debug("Load yaml configuration file.");

                        YamlPropertiesFactoryBean yamlFactoryBean = new YamlPropertiesFactoryBean();
                        yamlFactoryBean.setResources(resource);
                        configProperties = yamlFactoryBean.getObject();

                        assert configProperties != null;
                        getLog().debug("Yaml configuration loaded. " + configProperties);
                        break;
                    case "properties":
                        getLog().debug("Load properties configuration file.");

                        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
                        propertiesFactoryBean.setLocation(resource);
                        configProperties = propertiesFactoryBean.getObject();

                        assert configProperties != null;
                        getLog().debug("Properties configuration loaded. " + configProperties);
                        break;
                    case "xml":
                        throw new NotImplementedException("XML file extension is not implemented yet.");
                    default:
                        throw new Exception("Property file extension is not valid.");
                }

                // Add as spring DS URL
                configProperties.put("spring.datasource.url", configProperties.getProperty("obridge.jdbc-url"));

                applicationStartupProperties.putAll(configProperties);

                getLog().info("Configurations from property file loaded.");
                getLog().debug("Current startup properties: " + applicationStartupProperties);
            } else {
                getLog().info("Can not load properties from yaml or properties file.");
            }
        } catch (Exception e) {
            getLog().error(e);
        }

        // Override properties from plugin configuration
        getLog().debug("Override properties from plugin configuration." + applicationStartupProperties);
        Optional.ofNullable(jdbcUrl).ifPresent(value -> applicationStartupProperties.put("spring.datasource.url", value));
        Optional.ofNullable(sourceRoot).ifPresent(value -> applicationStartupProperties.put("obridge.source-root", value));
        Optional.ofNullable(rootPackageName).ifPresent(value -> applicationStartupProperties.put("obridge.root-package-name", value));
        Optional.of(generateNestedTypes).ifPresent(value -> applicationStartupProperties.put("obridge.generate-nested-types", value));
        Optional.ofNullable(packages).ifPresent(value -> {
            Optional.ofNullable(value.getEntityObjects()).ifPresent(val -> applicationStartupProperties.put("obridge.packages.entity-objects", val));
            Optional.ofNullable(value.getPackageObjects()).ifPresent(val -> applicationStartupProperties.put("obridge.packages.package-objects", val));
            Optional.ofNullable(value.getConverterObjects()).ifPresent(val -> applicationStartupProperties.put("obridge.packages.converter-objects", val));
            Optional.ofNullable(value.getProcedureContextObjects()).ifPresent(val -> applicationStartupProperties.put("obridge.packages.procedure-context-objects", val));
        });
        Optional.ofNullable(includes).ifPresent(values -> IntStream.range(0, includes.size()).forEach(index -> {
            applicationStartupProperties.put("obridge.includes[" + index + "].owner", includes.get(index).getOwner());
            applicationStartupProperties.put("obridge.includes[" + index + "].name", includes.get(index).getName());
        }));


        getLog().debug("Obridge startup properties " + applicationStartupProperties);

        SpringApplicationBuilder application = new SpringApplicationBuilder(OBridge.class)
                .web(WebApplicationType.NONE)
                .logStartupInfo(false)
                .properties(applicationStartupProperties);

        application.run();

        project.addCompileSourceRoot(sourceRoot);
        project.addTestCompileSourceRoot(sourceRoot);
    }

}
