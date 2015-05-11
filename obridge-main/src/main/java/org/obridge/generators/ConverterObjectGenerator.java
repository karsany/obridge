package org.obridge.generators;

import org.apache.commons.io.FileUtils;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.dao.TypeDao;
import org.obridge.model.data.Type;
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
            String packageName = c.getRootPackageName() + "." + c.getPackages().getConverterObjects();
            String objectPackage = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
            String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";

            TypeDao td = new TypeDao(DataSourceProvider.getDataSource(c.getJdbcUrl()));

            // Standard Object Types
            List<String> types = td.getTypeList();

            for (String typeName : types) {
                Type t = new Type();
                t.setTypeName(typeName);
                t.setAttributeList(td.getTypeAttributes(typeName));
                t.setConverterPackageName(packageName);
                t.setObjectPackage(objectPackage);
                String javaSource = MustacheRunner.build("converter.mustache", t);
                FileUtils.writeStringToFile(new File(outputDir + t.getJavaClassName() + "Converter.java"), CodeFormatter.format(javaSource));
            }

            // Embedded Object Types
            List<String> embeddedTypeList = td.getEmbeddedTypeList();

            for (String typeName : embeddedTypeList) {
                Type t = new Type();
                t.setTypeName(typeName);
                t.setAttributeList(td.getEmbeddedTypeAttributes(typeName));
                t.setConverterPackageName(packageName);
                t.setObjectPackage(objectPackage);
                String javaSource = MustacheRunner.build("converter.mustache", t);
                FileUtils.writeStringToFile(new File(outputDir + t.getJavaClassName() + "Converter.java"), CodeFormatter.format(javaSource));
            }


            // Primitive list types
            Pojo pojo = new Pojo();
            pojo.setPackageName(packageName);
            String javaSource = MustacheRunner.build("PrimitiveTypeConverter.java.mustache", pojo);
            FileUtils.writeStringToFile(new File(outputDir + "PrimitiveTypeConverter.java"), CodeFormatter.format(javaSource));
        } catch (PropertyVetoException e) {
            throw new OBridgeException(e);
        } catch (IOException e) {
            throw new OBridgeException(e);
        }
    }


}
