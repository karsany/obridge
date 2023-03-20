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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.generators.ConverterObjectGenerator;
import org.obridge.generators.EntityObjectGenerator;
import org.obridge.generators.PackageObjectGenerator;
import org.obridge.generators.ProcedureContextGenerator;
import org.obridge.util.OBridgeException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Properties;

@Slf4j
@RequiredArgsConstructor
public class OBridge {

    private final ProcedureContextGenerator procedureContextGenerator;
    private final ConverterObjectGenerator converterObjectGenerator;
    private final EntityObjectGenerator entityObjectGenerator;
    private final PackageObjectGenerator packageObjectGenerator;
    private final OBridgeConfiguration oBridgeConfiguration;

    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");

        ApplicationContext ac = new AnnotationConfigApplicationContext("org.obridge.**");

        OBridge bean = ac.getBean(OBridge.class);
        bean.run(args);

        log.info("APPLICATION FINISHED");
    }

    public void run(String... args) {

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

            if (!ObjectUtils.isEmpty(oBridgeConfiguration)) {
                generate(oBridgeConfiguration);
            } else {
                printHelp(o);
            }

        } catch (ParseException e) {
            throw new OBridgeException("Exception in OBridge Main progam", e);
        }

    }

    private void printVersion() {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("git.properties"));
            log.info("OBridge Version {}\n" +
                     "OBridge BuildTime {}\n" +
                     "OBridge Abbrev {}\n" +
                     "OBridge Remote {}",
                    properties.get("git.build.version"),
                    properties.get("git.build.time"),
                    properties.get("git.commit.id.abbrev"),
                    properties.get("git.remote.origin.url"));
        } catch (IOException e) {
            log.error("Error occurred when loading version informations.");
            throw new OBridgeException(e);
        }
    }

    private void printHelp(Options o) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("obridge", o, true);
    }

    private CommandLine getCommandLine(Options o, String[] args) throws ParseException {


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

        CommandLineParser parser = new DefaultParser();
        return parser.parse(o, args);
    }

    public void generate(OBridgeConfiguration c) {

        // generate contexts
        procedureContextGenerator.generate(c);

        // generate packages
        packageObjectGenerator.generate(c);

        // generate objects
        entityObjectGenerator.generate(c);

        // generate converters
        converterObjectGenerator.generate(c);

    }

}
