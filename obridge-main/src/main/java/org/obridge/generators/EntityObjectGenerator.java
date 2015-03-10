package org.obridge.generators;

import org.apache.commons.io.FileUtils;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.dao.TypeDao;
import org.obridge.mappers.PojoMapper;
import org.obridge.model.generator.Pojo;
import org.obridge.util.CodeFormatter;
import org.obridge.util.DataSourceProvider;
import org.obridge.util.MustacheRunner;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by fkarsany on 2015.01.28..
 */
public class EntityObjectGenerator {

    public static void generate(OBridgeConfiguration c) throws SQLException, IOException, PropertyVetoException {

        String packageName = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
        String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";

        TypeDao td = new TypeDao(DataSourceProvider.getDataSource(c.getJdbcUrl()));

        List<String> types = td.getTypeList();

        for (String typeName : types) {
            Pojo pojo = PojoMapper.typeToPojo(typeName, td.getTypeAttributes(typeName));
            pojo.setPackageName(packageName);
            String javaSource = MustacheRunner.build("pojo.mustache", pojo);
            FileUtils.writeStringToFile(new File(outputDir + pojo.getClassName() + ".java"), CodeFormatter.format(javaSource));
        }

    }

}
