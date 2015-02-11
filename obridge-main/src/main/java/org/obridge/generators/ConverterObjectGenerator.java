package org.obridge.generators;

import java.beans.PropertyVetoException;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.io.FileUtils;
import org.obridge.dao.TypeDao;
import org.obridge.model.data.Type;
import org.obridge.util.MustacheRunner;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.util.DataSourceProvider;

/**
 * Created by fkarsany on 2015.01.28..
 */
public class ConverterObjectGenerator {

    public static void generate(OBridgeConfiguration c) throws SQLException, IOException, PropertyVetoException {
        OracleDataSource oracleDataSource = new OracleDataSource();
        oracleDataSource.setURL(c.getJdbcUrl());

        String packageName = c.getRootPackageName() + "." + c.getPackages().getConverterObjects();
        String objectPackage = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
        String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";

        TypeDao td = new TypeDao(DataSourceProvider.getDataSource(c.getJdbcUrl()));

        List<String> types = td.getTypeList();

        for (String typeName : types) {
            Type t = new Type();
            t.setTypeName(typeName);
            t.setAttributeList(td.getTypeAttributes(typeName));
            t.setConverterPackageName(packageName);
            t.setObjectPackage(objectPackage);
            String converterString = MustacheRunner.build("converter.mustache", t);
            FileUtils.writeStringToFile(new File(outputDir + t.getJavaClassName() + "Converter.java"), converterString);
        }
    }
}
