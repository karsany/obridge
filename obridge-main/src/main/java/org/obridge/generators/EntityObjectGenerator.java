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
import org.obridge.mappers.PojoMapper;
import org.obridge.model.data.TypeAttribute;
import org.obridge.model.dto.TypeIdDto;
import org.obridge.model.generator.Pojo;
import org.obridge.util.MustacheRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fkarsany on 2015.01.28..
 */
@Slf4j
@Component
@RequiredArgsConstructor
public final class EntityObjectGenerator {

    private final TypeDao typeDao;

    public void generate(OBridgeConfiguration c) {
        String packageName = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
        String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";


        List<TypeIdDto> rootTypes = typeDao.getTypeList(c);
        findAndGenTypes(packageName, outputDir, rootTypes, c.isGenerateNestedTypes(), c.isGenerateGenerationDates());

        if (rootTypes.size() == 0) {
            generateEntityObject(packageName, outputDir, "Dummy", new ArrayList<>(), c.isGenerateGenerationDates());
        }
    }

    private void findAndGenTypes(String packageName, String outputDir, List<TypeIdDto> types, boolean generateNestedTypes, boolean generateGenerationDates) {
        for (TypeIdDto type : types) {
            List<TypeAttribute> typeAttributes = typeDao.getTypeAttributes(type);

            log.trace("EntityObjectGenerator.generate.typeAttributes {}", typeAttributes);

            generateEntityObject(packageName, outputDir, type.getTypeName(), typeAttributes, generateGenerationDates);
            if (generateNestedTypes) {
                for (TypeAttribute typeAttribute : typeAttributes) {
                    if (1 == typeAttribute.getMultiType() && !typeAttribute.isPrimitiveList())
                        findAndGenTypes(packageName, outputDir, List.of(
                                new TypeIdDto(
                                        typeAttribute.getOwner(),
                                        "COLLECTION".equals(typeAttribute.getTypeCode())
                                                ? typeAttribute.getCollectionBaseType()
                                                : typeAttribute.getAttrTypeName())), true, generateGenerationDates);
                }
            } else {
                log.info("Nested type generation skipped.");
            }
        }
    }

    private static void generateEntityObject(String packageName,
                                             String outputDir,
                                             String typeName,
                                             List<TypeAttribute> typeAttributes,
                                             boolean generateGenerationDates) {
        Pojo pojo = PojoMapper.typeToPojo(typeName, typeAttributes);
        pojo.setPackageName(packageName);
        pojo.setGeneratorName("org.obridge.generators.EntityObjectGenerator");
        pojo.getImports().add("javax.annotation.processing.Generated");
        if(generateGenerationDates) {
            pojo.setCurrentDateTime(LocalDateTime.now());
        }
        MustacheRunner.build("pojo.mustache", pojo, Path.of(outputDir + pojo.getClassName() + ".java"));
    }

}
