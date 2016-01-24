package org.obridge.generators;

import org.apache.commons.io.FileUtils;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.dao.ProcedureDao;
import org.obridge.model.data.OraclePackage;
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
public final class PackageObjectGenerator {

    private PackageObjectGenerator() {
    }

    public static void generate(OBridgeConfiguration c) {
        try {

            String packageName = c.getRootPackageName() + "." + c.getPackages().getPackageObjects();
            String contextPackage = c.getRootPackageName() + "." + c.getPackages().getProcedureContextObjects();
            String converterPackage = c.getRootPackageName() + "." + c.getPackages().getConverterObjects();
            String objectPackage = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
            String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";

            List<OraclePackage> allPackages = new ProcedureDao(DataSourceProvider.getDataSource(c.getJdbcUrl())).getAllPackages();

            for (OraclePackage oraclePackage : allPackages) {
                generatePackageObject(packageName, contextPackage, converterPackage, objectPackage, outputDir, oraclePackage);
            }
        } catch (PropertyVetoException e) {
            throw new OBridgeException(e);
        } catch (IOException e) {
            throw new OBridgeException(e);
        }
    }

    private static void generatePackageObject(String packageName, String contextPackage, String converterPackage, String objectPackage, String outputDir, OraclePackage oraclePackage) throws IOException {
        oraclePackage.setJavaPackageName(packageName);
        oraclePackage.setContextPackage(contextPackage);
        oraclePackage.setConverterPackage(converterPackage);
        oraclePackage.setObjectPackage(objectPackage);
        String javaSource = MustacheRunner.build("package.mustache", oraclePackage);
        FileUtils.writeStringToFile(new File(outputDir + oraclePackage.getJavaClassName() + ".java"), CodeFormatter.format(javaSource));
    }
}
