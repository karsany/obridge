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

            List<OraclePackage> allPackages = new ProcedureDao(DataSourceProvider.getDataSource(c.getJdbcUrl())).getAllPackages();

            for (OraclePackage oraclePackage : allPackages) {
                generatePackageObject(packageName, contextPackage, converterPackage, objectPackage, outputDir, oraclePackage);
            }

            generateStoredProcedureCallExceptionClass(packageName, outputDir);

        } catch (PropertyVetoException e) {
            throw new OBridgeException(e);
        } catch (IOException e) {
            throw new OBridgeException(e);
        }
    }

    private static void generatePackageObject(String packageName, String contextPackage, String converterPackage, String objectPackage, String outputDir, OraclePackage oraclePackage) throws IOException {
        oraclePackage.setJavaPackageName(packageName);
        oraclePackage.setContextPackage(contextPackage);
        oraclePackage.setConverterPackage(converterPackage);
        oraclePackage.setObjectPackage(objectPackage);
        String javaSource = MustacheRunner.build("package.mustache", oraclePackage);
        FileUtils.writeStringToFile(new File(outputDir + oraclePackage.getJavaClassName() + ".java"), CodeFormatter.format(javaSource));
    }

    private static void generateStoredProcedureCallExceptionClass(String packageName, String outputDir) throws IOException {
        OraclePackage op = new OraclePackage();
        op.setJavaPackageName(packageName);
        String javaSource = MustacheRunner.build("StoredProcedureCallException.java.mustache", op);
        FileUtils.writeStringToFile(new File(outputDir + "StoredProcedureCallException.java"), CodeFormatter.format(javaSource));
    }
}
