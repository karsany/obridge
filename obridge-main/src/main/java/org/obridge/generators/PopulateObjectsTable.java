package org.obridge.generators;

import org.obridge.context.OBridgeConfiguration;
import org.obridge.util.DataSourceProvider;
import org.obridge.util.OBridgeException;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.CallableStatement;

public class PopulateObjectsTable {

    public static void run(OBridgeConfiguration c) {
        String sourcesTableProc = c.getSourcesTableProc();
        String projectName = c.getProjectName();
        if (sourcesTableProc == null) {
            return;
        }
        sourcesTableProc = "begin " + sourcesTableProc + "(?); end;";
        try {
            DataSource datasource = DataSourceProvider.getDataSource(c.getJdbcUrl());
            CallableStatement statement = datasource.getConnection().prepareCall(sourcesTableProc);
            statement.setString(1, projectName);
            statement.execute();
        } catch (Exception e) {
            throw new OBridgeException(e);
        }
    }
}
