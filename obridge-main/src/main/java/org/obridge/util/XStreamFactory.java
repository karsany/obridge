package org.obridge.util;

import com.thoughtworks.xstream.XStream;
import org.obridge.context.OBridgeConfiguration;

/**
 * @author fkarsany
 */
public class XStreamFactory {

    private XStreamFactory() {
    }

    public static XStream createXStream() {
        XStream xStream = new XStream();

        xStream.alias("configuration", OBridgeConfiguration.class);

        return xStream;
    }

}
