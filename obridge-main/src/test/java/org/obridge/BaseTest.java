package org.obridge;

import oracle.jdbc.pool.OracleDataSource;
import org.junit.Before;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by fkarsany on 2015.01.03..
 */

public abstract class BaseTest {

    protected OracleDataSource ds;
    protected String connectionString;
    private Properties p;

    @Before
    public void init() throws SQLException, IOException {
        p = new Properties();
        p.load(getClass().getClassLoader().getResourceAsStream("datasource.properties"));
        connectionString = p.getProperty("connectionString");
        ds = new OracleDataSource();
        ds.setURL(connectionString);
    }

}
