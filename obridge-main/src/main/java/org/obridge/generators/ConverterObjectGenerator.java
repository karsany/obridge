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
import org.obridge.dao.TypeDao;
import org.obridge.model.data.Type;
import org.obridge.model.data.TypeAttribute;
import org.obridge.model.dto.TypeIdDto;
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
public final class ConverterObjectGenerator {

    private ConverterObjectGenerator() {
    }

    public static void generate(OBridgeConfiguration c) {
        try {
            String packageName   = c.getRootPackageName() + "." + c.getPackages().getConverterObjects();
            String objectPackage = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
            String outputDir     = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";

            TypeDao typeDao = new TypeDao(DataSourceProvider.getDataSource(c.getJdbcUrl()));

            List<TypeIdDto> types = typeDao.getTypeList(c);
            for (TypeIdDto t : types) {
                generateType(packageName, objectPackage, outputDir, t, typeDao.getTypeAttributes(t), c);
            }

            /*if (OBridgeConfiguration.GENERATE_SOURCE_FOR_PLSQL_TYPES) {
                List<String> embeddedTypes = typeDao.getEmbeddedTypeList(c.getSourceOwner());
                for (String typeName : embeddedTypes) {
                    generateType(packageName, objectPackage, outputDir, typeName,
                                 typeDao.getEmbeddedTypeAttributes(typeName, c.getSourceOwner()));
                }
            }*/

            generatePrimitiveTypeConverter(packageName, outputDir, c);

        } catch (PropertyVetoException | IOException e) {
            throw new OBridgeException(e);
        }
    }

    private static void generatePrimitiveTypeConverter(String packageName, String outputDir, OBridgeConfiguration c) throws IOException {
        Pojo pojo = new Pojo();
        pojo.setPackageName(packageName);
        pojo.setConfiguration(c);
        String javaSource = MustacheRunner.build("PrimitiveTypeConverter.java.mustache", pojo);
        FileUtils.writeStringToFile(new File(outputDir + "PrimitiveTypeConverter.java"), CodeFormatter.format(javaSource), "utf-8");
    }

    private static void generateType(String packageName,
                                     String objectPackage,
                                     String outputDir,
                                     TypeIdDto type,
                                     List<TypeAttribute> typeAttributes, OBridgeConfiguration c) throws IOException {
        Type t = new Type();
        t.setOwner(type.getOwner());
        t.setTypeName(type.getTypeName());
        t.setAttributeList(typeAttributes);
        t.setConverterPackageName(packageName);
        t.setObjectPackage(objectPackage);
        t.setConfiguration(c);
        String javaSource = MustacheRunner.build("converter.mustache", t);
        FileUtils.writeStringToFile(new File(outputDir + t.getJavaClassName() + "Converter.java"), CodeFormatter.format(javaSource),
                                    "utf-8");
    }

}
