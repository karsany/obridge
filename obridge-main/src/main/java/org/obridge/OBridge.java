package org.obridge;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.generators.ConverterObjectGenerator;
import org.obridge.generators.EntityObjectGenerator;
import org.obridge.generators.PackageObjectGenerator;
import org.obridge.generators.ProcedureContextGenerator;
import org.obridge.util.XStreamFactory;

/**
 * Hello world!
 */
public class OBridge {

    public void generate(OBridgeConfiguration c) throws SQLException, IOException {
        // generate objects
        EntityObjectGenerator.generate(c);

        // generate converters
        ConverterObjectGenerator.generate(c);

        // generate contexts
        ProcedureContextGenerator.generate(c);

        // generate packages
        PackageObjectGenerator.generate(c);

    }

    public static void main(String[] args) throws SQLException, IOException {
        XStream xs = XStreamFactory.createXStream();
        Object config = xs.fromXML(new File(args[0]));
        new OBridge().generate((OBridgeConfiguration) config);
    }
}
