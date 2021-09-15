package org.obridge;

import oracle.jdbc.pool.OracleDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Before;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by fkarsany on 2015.01.03..
 */

public abstract class BaseTest {

    private static boolean flywayInitialized = false;
    protected OracleDataSource ds;
    protected String connectionString;
    private Properties p;

    @Before
    public void init() throws IOException, SQLException {
        if (ds == null) {
            p = new Properties();
            p.load(getClass().getClassLoader().getResourceAsStream("datasource.properties"));
            connectionString = p.getProperty("connectionString");
            ds = new OracleDataSource();
            ds.setURL(connectionString);
        }

        if (!flywayInitialized) {
            Flyway load = Flyway.configure().dataSource(this.ds).load();
            load.clean();
            load.migrate();
            flywayInitialized = true;
        }

    }

}
