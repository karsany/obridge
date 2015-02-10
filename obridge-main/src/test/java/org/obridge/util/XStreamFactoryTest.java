/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obridge.util;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.obridge.BaseTest;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.context.Packages;

/**
 *
 * @author fkarsany
 */
public class XStreamFactoryTest extends BaseTest {

    /**
     * Test of createXStream method, of class XStreamFactory.
     */
    @Test
    public void testCreateXStream() throws IOException {
        XStream x = XStreamFactory.createXStream();

        OBridgeConfiguration obc = new OBridgeConfiguration();
        obc.setJdbcUrl(connectionString);
        obc.setSourceRoot(File.createTempFile("ObjectGenerator", Long.toString(System.nanoTime())).getParentFile().toString());
        obc.setRootPackageName("hu.obridge.test");
        obc.setPackages(new Packages());

        String s = x.toXML(obc);
        
        System.out.println(s);

        Assert.assertTrue("starts with <configuration>", s.startsWith("<configuration>"));

    }

}
