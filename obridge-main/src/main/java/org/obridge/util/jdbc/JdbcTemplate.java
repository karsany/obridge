package org.obridge.util.jdbc;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fkarsany on 2015.03.04..
 */
public class JdbcTemplate {

    private final DataSource dataSource;

    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> queryForList(String sql) throws JdbcTemplateException {
        return query(sql, new RowMapper<T>() {
            @Override
            public T mapRow(ResultSet resultSet, int i) throws SQLException {
                return (T) resultSet.getObject(1);
            }
        });
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws JdbcTemplateException {
        return query(sql, null, rowMapper);
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) throws JdbcTemplateException {
        return query(sql, args, rowMapper);
    }

    public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws JdbcTemplateException {

        List<T> ret = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(sql);

            bindParameters(args, ps);

            resultSet = ps.executeQuery();

            ret = fetchData(rowMapper, resultSet);

            resultSet.close();
            resultSet = null;
            ps.close();
            ps = null;
            connection.close();
            connection = null;

            return ret;

        } catch (SQLException e) {
            tryCloseConnection(connection, ps, resultSet);
            throw new JdbcTemplateException(e);
        }
    }

    private void tryCloseConnection(Connection connection, PreparedStatement ps, ResultSet resultSet) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }

        } catch (SQLException ex) {
            throw new JdbcTemplateException("Cannot close the database", ex);
        }
    }

    private <T> List<T> fetchData(RowMapper<T> rowMapper, ResultSet resultSet) throws SQLException {
        List<T> ret;
        ret = new ArrayList<T>();
        int i = 0;
        while (resultSet.next()) {
            i++;
            ret.add(rowMapper.mapRow(resultSet, i));
        }
        return ret;
    }

    private void bindParameters(Object[] args, PreparedStatement ps) throws SQLException {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
        }
    }

}
