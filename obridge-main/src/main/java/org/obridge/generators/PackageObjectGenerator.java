package org.obridge.generators;

import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.io.FileUtils;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.dao.ProcedureDao;
import org.obridge.model.data.OraclePackage;
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
public class PackageObjectGenerator {

    public static void generate(OBridgeConfiguration c) throws SQLException, IOException, PropertyVetoException {

        OracleDataSource oracleDataSource = new OracleDataSource();
        oracleDataSource.setURL(c.getJdbcUrl());

        String packageName = c.getRootPackageName() + "." + c.getPackages().getPackageObjects();
        String contextPackage = c.getRootPackageName() + "." + c.getPackages().getProcedureContextObjects();
        String converterPackage = c.getRootPackageName() + "." + c.getPackages().getConverterObjects();
        String objectPackage = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
        String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";

        List<OraclePackage> allPackages = new ProcedureDao(DataSourceProvider.getDataSource(c.getJdbcUrl())).getAllPackages();

        for (OraclePackage oraclePackage : allPackages) {
            oraclePackage.setJavaPackageName(packageName);
            oraclePackage.setContextPackage(contextPackage);
            oraclePackage.setConverterPackage(converterPackage);
            oraclePackage.setObjectPackage(objectPackage);
            String javaSource = MustacheRunner.build("package.mustache", oraclePackage);
            FileUtils.writeStringToFile(new File(outputDir + oraclePackage.getJavaClassName() + ".java"), CodeFormatter.format(javaSource));
        }
    }
}
