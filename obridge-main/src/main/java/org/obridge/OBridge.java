package org.obridge;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.cli.*;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.generators.ConverterObjectGenerator;
import org.obridge.generators.EntityObjectGenerator;
import org.obridge.generators.PackageObjectGenerator;
import org.obridge.generators.ProcedureContextGenerator;
import org.obridge.util.XStreamFactory;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class OBridge {

    public void generate(OBridgeConfiguration c) throws SQLException, IOException, PropertyVetoException {
        // generate objects
        EntityObjectGenerator.generate(c);

        // generate converters
        ConverterObjectGenerator.generate(c);

        // generate contexts
        ProcedureContextGenerator.generate(c);

        // generate packages
        PackageObjectGenerator.generate(c);

    }

    public OBridgeConfiguration loadConfiguration(File f) {
        XStream xs = XStreamFactory.createXStream();
        Object config = xs.fromXML(f);
        return (OBridgeConfiguration) config;
    }

    public void generate(File f) throws IOException, SQLException, PropertyVetoException {
        this.generate(loadConfiguration(f));
    }

    public static void main(String... args) throws SQLException, IOException, PropertyVetoException, ParseException {

        Options o = new Options();
        o.addOption("v", "version", false, "print version number");
        o.addOption("h", "help", false, "prints this help");
        o.addOption(OptionBuilder
                .withArgName("file")
                .hasArg()
                .withDescription("OBridge XML config file")
                .withLongOpt("config")
                .create("c"));

        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse(o, args);

        if (cmd.hasOption("h")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("obridge", o, true);
            return;
        }

        if (cmd.hasOption("v")) {
            final Properties properties = new Properties();
            properties.load(new OBridge().getClass().getResourceAsStream("obridge-project.properties"));
            System.out.println("OBridge version " + properties.getProperty("version"));
            return;
        }

        if (cmd.hasOption("c")) {
            new OBridge().generate(new File(cmd.getOptionValue("c")));
        } else {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("obridge", o, true);
        }
    }
}
