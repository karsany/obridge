package org.obridge.util;

/**
 * Created by fkarsany on 2015.03.23..
 */
public class OBridgeException extends RuntimeException {

    public OBridgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OBridgeException(Throwable cause) {
        super(cause);
    }

    public OBridgeException(String message) {
        super(message);
    }
}
