package org.obridge.generators;

import org.apache.commons.io.FileUtils;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.dao.TypeDao;
import org.obridge.mappers.PojoMapper;
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
public final class EntityObjectGenerator {

    private EntityObjectGenerator() {
    }

    public static void generate(OBridgeConfiguration c) {
        try {
            String packageName = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
            String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";

            TypeDao typeDao = new TypeDao(DataSourceProvider.getDataSource(c.getJdbcUrl()));

            List<String> types = typeDao.getTypeList();

            for (String typeName : types) {
                generateEntityObject(packageName, outputDir, typeDao, typeName);
            }
        } catch (PropertyVetoException e) {
            throw new OBridgeException(e);
        } catch (IOException e) {
            throw new OBridgeException(e);
        }
    }

    private static void generateEntityObject(String packageName, String outputDir, TypeDao typeDao, String typeName) throws IOException {
        Pojo pojo = PojoMapper.typeToPojo(typeName, typeDao.getTypeAttributes(typeName));
        pojo.setPackageName(packageName);
        String javaSource = MustacheRunner.build("pojo.mustache", pojo);
        FileUtils.writeStringToFile(new File(outputDir + pojo.getClassName() + ".java"), CodeFormatter.format(javaSource));
    }

}
