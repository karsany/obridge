/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obridge;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @author fkarsany
 */
@Slf4j
public class OBridgeTest {

    @TempDir
    public Path tempdir;

    public OBridgeTest() {
    }

    @Test
    @Disabled
    public void fullTest() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("oconf-test.properties"));
        properties.put("obridge.source-root", tempdir.toAbsolutePath().toString());

        new SpringApplicationBuilder(OBridge.class)
                .web(WebApplicationType.NONE)
                .logStartupInfo(false)
                .properties(properties)
                .run();

        Assertions.assertTrue(new File(tempdir.toAbsolutePath() + "\\org\\obridge\\test\\converters\\PrimitiveTypeConverter.java").exists());
        Assertions.assertTrue(new File(tempdir.toAbsolutePath() + "\\org\\obridge\\test\\packages\\ProceduresAndFunctions.java").exists());
        Assertions.assertTrue(new File(tempdir.toAbsolutePath() + "\\org\\obridge\\test\\packages\\TestPackage.java").exists());
    }

}
