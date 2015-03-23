package org.obridge.util.jdbc;

/**
 * Created by fkarsany on 2015.03.23..
 */
public class JdbcTemplateException extends RuntimeException {

    public JdbcTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcTemplateException(Throwable cause) {
        super(cause);
    }
}
