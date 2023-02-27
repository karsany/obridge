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
import org.obridge.mappers.PojoMapper;
import org.obridge.model.data.Procedure;
import org.obridge.model.generator.Pojo;
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
public final class ProcedureContextGenerator {

    private final ProcedureDao procedureDao;

    public void generate(OBridgeConfiguration c) {
        String packageName = c.getRootPackageName() + "." + c.getPackages().getProcedureContextObjects();
        String objectPackage = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
        String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";

        List<Procedure> allProcedures = procedureDao.getAllProcedure(c.getIncludes());

        for (Procedure p : allProcedures) {
            if (c.isGenerateNestedTypes()) {
                p.getArgumentList().forEach(procedureArgument -> {
                    if (procedureArgument.getTypeName() != null) {
                        boolean notFound = c.getIncludes().stream().noneMatch(dbObject -> dbObject.getName().equals(procedureArgument.getTypeName()));
                        if (notFound) {
                            log.trace("NOT FOUND proc arg in include list. {}", procedureArgument.getTypeName());
                            c.getIncludes().add(new OBridgeConfiguration.DbObject(p.getOwner(), procedureArgument.getTypeName()));
                        } else {
                            log.trace("Include list already contains proc arg {}", procedureArgument.getTypeName());
                        }
                    } else {
                        log.trace("procedureArgument.getTypeName() is null. Skipped. {}", procedureArgument);
                    }
                });
            }

            generateProcedureContext(packageName, objectPackage, outputDir, p);
        }
    }

    private static void generateProcedureContext(String packageName, String objectPackage, String outputDir, Procedure p) {
        Pojo pojo = PojoMapper.procedureToPojo(p);
        pojo.setPackageName(packageName);
        pojo.setGeneratorName("org.obridge.generators.ProcedureContextGenerator");
        pojo.setCurrentDateTime(LocalDateTime.now());
        pojo.getImports().add(objectPackage + ".*");
        pojo.getImports().add("javax.annotation.processing.Generated");
        MustacheRunner.build("pojo.mustache", pojo, Path.of(outputDir + pojo.getClassName() + ".java"));
    }
}
