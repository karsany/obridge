/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Ferenc Karsany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package org.obridge;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.cli.*;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.generators.*;
import org.obridge.util.OBridgeException;
import org.obridge.util.XStreamFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class OBridge {

    public static void main(String... args) {

        try {

            Options o = new Options();
            CommandLine cmd = getCommandLine(o, args);

            if (cmd.hasOption("h")) {
                printHelp(o);
                return;
            }

            if (cmd.hasOption("v")) {
                printVersion();
                return;
            }

            if (cmd.hasOption("c")) {
                new OBridge().generate(new File(cmd.getOptionValue("c")));
            } else {
                printHelp(o);
            }

        } catch (ParseException e) {
            throw new OBridgeException("Exception in OBridge Main progam", e);
        } catch (IOException e) {
            throw new OBridgeException("Exception in OBridge Main progam", e);
        }

    }

    private static void printVersion() throws IOException {
        final Properties properties = new Properties();
        properties.load(OBridge.class.getResourceAsStream("obridge-project.properties"));
        System.out.println("OBridge version " + properties.getProperty("version"));
    }

    private static void printHelp(Options o) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("obridge", o, true);
    }

    private static CommandLine getCommandLine(Options o, String[] args) throws ParseException {


        o.addOption(
                Option.builder("v")
                        .longOpt("version")
                        .desc("print version number")
                        .required(false)
                        .build()
        );
        o.addOption(
                Option.builder("h")
                        .longOpt("help")
                        .desc("prints this help")
                        .required(false)
                        .build()
        );
        o.addOption(
                Option.builder("c")
                        .desc("OBridge XML config file")
                        .longOpt("config")
                        .hasArg()
                        .argName("file")
                        .build()
        );

        CommandLineParser parser = new PosixParser();
        return parser.parse(o, args);
    }

    public void generate(OBridgeConfiguration c) {
        // populate objects table
        PopulateObjectsTable.run(c);

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

    public void generate(File f) {
        this.generate(loadConfiguration(f));
    }
}
