package org.obridge.test.database.packages;

import java.sql.SQLException;

public class StoredProcedureCallException extends RuntimeException {
    public StoredProcedureCallException(SQLException e) {
        throw new RuntimeException(e.getMessage(), e);
    }
}
