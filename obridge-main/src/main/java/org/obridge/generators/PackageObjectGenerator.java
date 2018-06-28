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

package org.obridge.generators;

import org.apache.commons.io.FileUtils;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.dao.ProcedureDao;
import org.obridge.model.data.OraclePackage;
import org.obridge.util.CodeFormatter;
import org.obridge.util.DataSourceProvider;
import org.obridge.util.MustacheRunner;
import org.obridge.util.OBridgeException;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by fkarsany on 2015.01.28..
 */
public final class PackageObjectGenerator {

    private PackageObjectGenerator() {
    }

    public static void generate(OBridgeConfiguration c) {
        try {

            String packageName = c.getRootPackageName() + "." + c.getPackages().getPackageObjects();
            String contextPackage = c.getRootPackageName() + "." + c.getPackages().getProcedureContextObjects();
            String converterPackage = c.getRootPackageName() + "." + c.getPackages().getConverterObjects();
            String objectPackage = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
            String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";
            String loggingClassInitializer = "";
            String loggingMethod = "";

            if (c.getLogging() != null) {
                if (c.getLogging().getInitializer() != null
                        && !c.getLogging().getInitializer().isEmpty()
                        && c.getLogging().getMethod() != null
                        && !c.getLogging().getMethod().isEmpty()) {
                    loggingClassInitializer = c.getLogging().getInitializer();
                    loggingMethod = c.getLogging().getMethod();
                }
            }

            List<OraclePackage> allPackages = new ProcedureDao(DataSourceProvider.getDataSource(c.getJdbcUrl())).getAllPackages(c.getPackagesLike());

            for (OraclePackage oraclePackage : allPackages) {
                oraclePackage.setJavaPackageName(packageName);
                oraclePackage.setContextPackage(contextPackage);
                oraclePackage.setConverterPackage(converterPackage);
                oraclePackage.setObjectPackage(objectPackage);

                if (!loggingClassInitializer.isEmpty() && !loggingMethod.isEmpty()) {
                    oraclePackage.setLoggingInitializer(String.format(loggingClassInitializer, oraclePackage.getJavaClassName()));
                    oraclePackage.setLoggingMethod(loggingMethod);
                }

                generatePackageObject(outputDir, oraclePackage);
            }

            generateStoredProcedureCallExceptionClass(packageName, outputDir);

        } catch (PropertyVetoException e) {
            throw new OBridgeException(e);
        } catch (IOException e) {
            throw new OBridgeException(e);
        }
    }

    private static void generatePackageObject(String outputDir, OraclePackage oraclePackage) throws IOException {
        String javaSource = MustacheRunner.build("package.mustache", oraclePackage);
        FileUtils.writeStringToFile(new File(outputDir + oraclePackage.getJavaClassName() + ".java"), CodeFormatter.format(javaSource), "utf-8");
    }

    private static void generateStoredProcedureCallExceptionClass(String packageName, String outputDir) throws IOException {
        OraclePackage op = new OraclePackage();
        op.setJavaPackageName(packageName);
        String javaSource = MustacheRunner.build("StoredProcedureCallException.java.mustache", op);
        FileUtils.writeStringToFile(new File(outputDir + "StoredProcedureCallException.java"), CodeFormatter.format(javaSource), "utf-8");
    }
}
