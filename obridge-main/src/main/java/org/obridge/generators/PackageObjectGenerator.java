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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.dao.ProcedureDao;
import org.obridge.model.data.OraclePackage;
import org.obridge.util.MustacheRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by fkarsany on 2015.01.28..
 */
@Slf4j
@Component
@RequiredArgsConstructor
public final class PackageObjectGenerator {

    private final ProcedureDao procedureDao;

    public void generate(OBridgeConfiguration c) {
        String packageName = c.getRootPackageName() + "." + c.getPackages().getPackageObjects();
        String contextPackage = c.getRootPackageName() + "." + c.getPackages().getProcedureContextObjects();
        String converterPackage = c.getRootPackageName() + "." + c.getPackages().getConverterObjects();
        String objectPackage = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
        String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";
        String loggingClassInitializer = "";
        String loggingMethod = "";

        if (c.getLogging() != null) {
            if (c.getLogging().getInitializer() != null && !c.getLogging().getInitializer().isEmpty() && c
                    .getLogging()
                    .getMethod() != null && !c.getLogging().getMethod().isEmpty()) {
                loggingClassInitializer = c.getLogging().getInitializer();
                loggingMethod = c.getLogging().getMethod();
            }
        }

        List<OraclePackage> allPackages = procedureDao.getAllPackages(c.getIncludes());

        for (OraclePackage oraclePackage : allPackages) {
            oraclePackage.setJavaPackageName(packageName);
            oraclePackage.setContextPackage(contextPackage);
            oraclePackage.setConverterPackage(converterPackage);
            oraclePackage.setObjectPackage(objectPackage);


            if (!loggingClassInitializer.isEmpty() && !loggingMethod.isEmpty()) {
                oraclePackage.setLoggingInitializer(String.format(loggingClassInitializer, oraclePackage.getJavaClassName()));
                oraclePackage.setLoggingMethod(loggingMethod);
            }

            oraclePackage.setCurrentDateTime(LocalDateTime.now());
            log.trace("FullOraclePackage: {}", oraclePackage);
            oraclePackage.getProcedureList().forEach(procedure -> {
                log.trace("BindParams: {}", procedure.getBindParams());
            });
            generatePackageObject(outputDir, oraclePackage);
        }

        generateStoredProcedureCallExceptionClass(packageName, outputDir);
    }

    private static void generatePackageObject(String outputDir, OraclePackage oraclePackage) {
        MustacheRunner.build("package.mustache", oraclePackage, Path.of(outputDir + oraclePackage.getJavaClassName() + ".java"));
        log.debug(" ... " + oraclePackage.getJavaClassName());
    }

    private static void generateStoredProcedureCallExceptionClass(String packageName, String outputDir) {
        OraclePackage op = new OraclePackage();
        op.setJavaPackageName(packageName);
        op.setCurrentDateTime(LocalDateTime.now());
        MustacheRunner.build("StoredProcedureCallException.java.mustache", op, Path.of(outputDir + "StoredProcedureCallException.java"));
        log.debug(" ... StoredProcedureCallException");
    }
}
