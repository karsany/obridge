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
import org.obridge.dao.TypeDao;
import org.obridge.model.data.Type;
import org.obridge.model.data.TypeAttribute;
import org.obridge.model.dto.TypeIdDto;
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
public final class ConverterObjectGenerator {

    private final TypeDao typeDao;


    public void generate(OBridgeConfiguration c) {
        log.trace("ConverterObjectGenerator.generate {}", c);
        String packageName = c.getRootPackageName() + "." + c.getPackages().getConverterObjects();
        String objectPackage = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
        String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";

        generatePrimitiveTypeConverter(packageName, outputDir);

        List<TypeIdDto> types = typeDao.getTypeList(c);
        findAndGenTypeConverter(packageName, objectPackage, outputDir, types, c.isGenerateNestedTypes());
    }

    private void findAndGenTypeConverter(String packageName, String objectPackage, String outputDir, List<TypeIdDto> types, boolean generateNestedTypes) {
        log.trace("ConverterObjectGenerator.generate.types {}", types);
        for (TypeIdDto t : types) {
            List<TypeAttribute> typeAttributes = typeDao.getTypeAttributes(t);
            log.trace("ConverterObjectGenerator.generate.typeAttributes {}", typeAttributes);

            generateType(packageName, objectPackage, outputDir, t, typeAttributes);
            if (generateNestedTypes) {
                for (TypeAttribute typeAttribute : typeAttributes) {
                    if (1 == typeAttribute.getMultiType() && !typeAttribute.isPrimitiveList())
                        findAndGenTypeConverter(packageName, objectPackage, outputDir, List.of(new TypeIdDto(
                                typeAttribute.getOwner(),
                                "COLLECTION".equals(typeAttribute.getTypeCode())
                                        ? typeAttribute.getCollectionBaseType()
                                        : typeAttribute.getAttrTypeName())), true);
                }
            } else {
                log.info("Nested typeconverter generation skipped.");
            }
        }
    }

    private static void generatePrimitiveTypeConverter(String packageName, String outputDir) {
        Pojo pojo = new Pojo();
        pojo.setPackageName(packageName);
        pojo.setClassName("PrimitiveTypeConverter");
        pojo.setCurrentDateTime(LocalDateTime.now());

        MustacheRunner.build("PrimitiveTypeConverter.java.mustache", pojo, Path.of(outputDir + pojo.getClassName() + ".java"));
    }

    private static void generateType(String packageName,
                                     String objectPackage,
                                     String outputDir,
                                     TypeIdDto type,
                                     List<TypeAttribute> typeAttributes) {
        Type t = new Type();
        t.setOwner(type.getOwner());
        t.setTypeName(type.getTypeName());
        t.setAttributeList(typeAttributes);
        t.setConverterPackageName(packageName);
        t.setObjectPackage(objectPackage);
        t.setCurrentDateTime(LocalDateTime.now());

        MustacheRunner.build("converter.mustache", t, Path.of(outputDir + t.getJavaClassName() + "Converter.java"));
    }

}
