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
import org.obridge.mappers.PojoMapper;
import org.obridge.model.data.Procedure;
import org.obridge.model.generator.Pojo;
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
public final class ProcedureContextGenerator {

    private ProcedureContextGenerator() {
    }

    public static void generate(OBridgeConfiguration c) {

        try {
            String packageName = c.getRootPackageName() + "." + c.getPackages().getProcedureContextObjects();
            String objectPackage = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
            String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";

            ProcedureDao procedureDao = new ProcedureDao(DataSourceProvider.getDataSource(c.getJdbcUrl()));

            List<Procedure> allProcedures = procedureDao.getAllProcedure(c);

            for (Procedure p : allProcedures) {
                generateProcedureContext(packageName, objectPackage, outputDir, p);
            }
        } catch (PropertyVetoException | IOException e) {
            throw new OBridgeException(e);
        }
    }

    private static void generateProcedureContext(String packageName, String objectPackage, String outputDir, Procedure p) throws IOException {
        Pojo pojo = PojoMapper.procedureToPojo(p);
        pojo.setPackageName(packageName);
        pojo.setGeneratorName("org.obridge.generators.ProcedureContextGenerator");
        pojo.getImports().add(objectPackage + ".*");
        pojo.getImports().add("javax.annotation.Generated");
        String javaSource = MustacheRunner.build("pojo.mustache", pojo);
        FileUtils.writeStringToFile(new File(outputDir + pojo.getClassName() + ".java"), CodeFormatter.format(javaSource), "utf-8");
    }
}
