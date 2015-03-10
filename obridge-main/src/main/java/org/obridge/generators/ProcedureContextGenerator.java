package org.obridge.generators;

import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.io.FileUtils;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.dao.ProcedureDao;
import org.obridge.mappers.PojoMapper;
import org.obridge.model.data.Procedure;
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
public class ProcedureContextGenerator {

    public static void generate(OBridgeConfiguration c) throws SQLException, IOException, PropertyVetoException {
        OracleDataSource oracleDataSource = new OracleDataSource();
        oracleDataSource.setURL(c.getJdbcUrl());

        String packageName = c.getRootPackageName() + "." + c.getPackages().getProcedureContextObjects();
        String objectPackage = c.getRootPackageName() + "." + c.getPackages().getEntityObjects();
        String outputDir = c.getSourceRoot() + "/" + packageName.replace(".", "/") + "/";

        ProcedureDao procedureDao = new ProcedureDao(DataSourceProvider.getDataSource(c.getJdbcUrl()));

        List<Procedure> allProcedures = procedureDao.getAllProcedures();

        for (Procedure p : allProcedures) {
            Pojo pojo = PojoMapper.procedureToPojo(p);
            pojo.setPackageName(packageName);
            pojo.getImports().add(objectPackage + ".*");
            String javaSource = MustacheRunner.build("pojo.mustache", pojo);
            FileUtils.writeStringToFile(new File(outputDir + pojo.getClassName() + ".java"), CodeFormatter.format(javaSource));
        }

    }
}
