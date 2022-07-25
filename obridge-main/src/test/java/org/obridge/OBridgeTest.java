/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obridge;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.context.Packages;
import org.obridge.dao.ProcedureDao;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author fkarsany
 */
public class OBridgeTest {

    @Rule
    public TemporaryFolder tempdir = new TemporaryFolder();

    public OBridgeTest() {
    }

    @Test
    public void testMain() {

        OBridge.main("-h");
        OBridge.main("-v");

    }


    @Test
    public void fullTest() throws IOException, InterruptedException {

        Properties p = new Properties();
        p.load(getClass().getClassLoader().getResourceAsStream("datasource.properties"));

        OBridgeConfiguration oBridgeConfiguration = new OBridgeConfiguration();
        oBridgeConfiguration.setJdbcUrl(p.getProperty("connectionString"));
        oBridgeConfiguration.setPackages(new Packages());
        oBridgeConfiguration.setRootPackageName("org.obridge.test");
        oBridgeConfiguration.setSourceRoot(tempdir.getRoot().getAbsolutePath());

        new OBridge().generate(oBridgeConfiguration);

        Assert.assertTrue(tempdir.getRoot().exists());
        Assert.assertTrue(new File(tempdir.getRoot().getAbsolutePath() + "\\org\\obridge\\test\\converters\\PrimitiveTypeConverter.java").exists());
        Assert.assertTrue(new File(tempdir.getRoot().getAbsolutePath() + "\\org\\obridge\\test\\packages\\ProceduresAndFunctions.java").exists());
        Assert.assertTrue(new File(tempdir.getRoot().getAbsolutePath() + "\\org\\obridge\\test\\packages\\TestPackage.java").exists());


    }

}
