/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obridge.util;

import com.thoughtworks.xstream.XStream;
import org.junit.Assert;
import org.junit.Test;
import org.obridge.BaseTest;
import org.obridge.context.DbObject;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.context.Packages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fkarsany
 */
public class XStreamFactoryTest {

    /**
     * Test of createXStream method, of class XStreamFactory.
     */
    @Test
    public void testCreateXStream() throws IOException {
        XStream x = XStreamFactory.createXStream();

        OBridgeConfiguration obc = new OBridgeConfiguration();
        obc.setJdbcUrl("jdbc:oracle:thin:obridge/obridge@127.0.0.1:1529/xepdb1");
        obc.setSourceRoot(File.createTempFile("ObjectGenerator", Long.toString(System.nanoTime())).getParentFile().toString());
        obc.setRootPackageName("hu.obridge.test");
        obc.setPackages(new Packages());

        List<DbObject> xx = new ArrayList<>();
        xx.add(new DbObject("ABCD", "EFGH"));
        obc.setDbObjects(xx);

        String s = x.toXML(obc);

        System.out.println(s);

        Assert.assertTrue("starts with <configuration>", s.startsWith("<configuration>"));

    }

}
