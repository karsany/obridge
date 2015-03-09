package org.obridge.generators;

import org.junit.Test;
import org.obridge.BaseTest;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.context.Packages;

import java.io.File;

public class EntityObjectGeneratorTest extends BaseTest {

    @Test
    public void testGenerate() throws Exception {
        OBridgeConfiguration c = new OBridgeConfiguration();
        c.setJdbcUrl(connectionString);
        c.setSourceRoot(File.createTempFile("ObjectGenerator", Long.toString(System.nanoTime())).getParentFile().toString());
        c.setRootPackageName("hu.obridge.test");
        c.setPackages(new Packages());

        EntityObjectGenerator.generate(c);
        ConverterObjectGenerator.generate(c);
        ProcedureContextGenerator.generate(c);
        PackageObjectGenerator.generate(c);
    }
}
