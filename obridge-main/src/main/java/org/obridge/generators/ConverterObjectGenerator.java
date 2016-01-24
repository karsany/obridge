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

            TypeDao typeDao = new TypeDao(DataSourceProvider.getDataSource(c.getJdbcUrl()));

            List<String> types = typeDao.getTypeList();

            for (String typeName : types) {
                generateType(packageName, objectPackage, outputDir, typeDao, typeName);
            }

            generatePrimitiveTypeConverter(packageName, outputDir);

        } catch (PropertyVetoException e) {
            throw new OBridgeException(e);
        } catch (IOException e) {
            throw new OBridgeException(e);
        }
    }

    private static void generatePrimitiveTypeConverter(String packageName, String outputDir) throws IOException {
        Pojo pojo = new Pojo();
        pojo.setPackageName(packageName);
        String javaSource = MustacheRunner.build("PrimitiveTypeConverter.java.mustache", pojo);
        FileUtils.writeStringToFile(new File(outputDir + "PrimitiveTypeConverter.java"), CodeFormatter.format(javaSource));
    }

    private static void generateType(String packageName, String objectPackage, String outputDir, TypeDao typeDao, String typeName) throws IOException {
        Type t = new Type();
        t.setTypeName(typeName);
        t.setAttributeList(typeDao.getTypeAttributes(typeName));
        t.setConverterPackageName(packageName);
        t.setObjectPackage(objectPackage);
        String javaSource = MustacheRunner.build("converter.mustache", t);
        FileUtils.writeStringToFile(new File(outputDir + t.getJavaClassName() + "Converter.java"), CodeFormatter.format(javaSource));
    }


}
