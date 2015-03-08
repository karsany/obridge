package org.obridge.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Before;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by fkarsany on 2015.01.03..
 */

public abstract class BaseTest {

    private Properties p;
    protected DataSource ds;
    protected String connectionString;

    @Before
    public void init() throws SQLException, IOException, PropertyVetoException {
        p = new Properties();
        p.load(getClass().getClassLoader().getResourceAsStream("datasource.properties"));
        connectionString = p.getProperty("connectionString");

        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setDriverClass("oracle.jdbc.OracleDriver"); //loads the jdbc driver
        ds.setJdbcUrl(connectionString);
        this.ds = ds;
    }

}
