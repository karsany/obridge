/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obridge;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author fkarsany
 */
public class OBridgeTest {

    public OBridgeTest() {
    }

    @Test
    public void testMain() throws SQLException, IOException, PropertyVetoException, ParseException {

        OBridge.main("-h");
        OBridge.main("-v");

    }

}
