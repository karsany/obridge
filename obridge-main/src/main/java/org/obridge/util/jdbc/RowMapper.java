package org.obridge.util.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fkarsany on 2015.03.04..
 */
public interface RowMapper<T> {
    T mapRow(ResultSet resultSet, int i) throws SQLException;
}
