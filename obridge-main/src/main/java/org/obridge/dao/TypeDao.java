package org.obridge.dao;

import org.obridge.model.data.TypeAttribute;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * User: fkarsany
 * Date: 2013.11.14.
 */
public class TypeDao {

    private JdbcTemplate jdbcTemplate;

    public TypeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<String> getTypeList() {
        return jdbcTemplate.queryForList("SELECT type_name FROM user_types WHERE typecode = 'OBJECT'", String.class);
    }

    public List<TypeAttribute> getTypeAttributes(String typeName) {
        return jdbcTemplate.query(
                "SELECT attr_name, attr_type_name, attr_no, nvl(scale,-1) data_scale, case when attr_type_owner is not null then 1 else 0 end multi_type, bb.typecode, " +
                        "(select elem_type_name from user_coll_types t where t.TYPE_NAME = aa.attr_type_name) collection_base_type " +
                        "FROM user_type_attrs aa, user_types bb " +
                        "WHERE UPPER(aa.type_name) = ? and aa.attr_type_name = bb.type_name(+) ORDER BY attr_no ASC",
                new Object[]{typeName.toUpperCase()}, new RowMapper<TypeAttribute>() {
                    @Override
                    public TypeAttribute mapRow(ResultSet resultSet, int i) throws SQLException {
                        return new TypeAttribute(
                                resultSet.getString("attr_name"),
                                resultSet.getString("attr_type_name"),
                                resultSet.getInt("attr_no"),
                                resultSet.getInt("data_scale"),
                                resultSet.getInt("multi_type"),
                                resultSet.getString("typecode"),
                                resultSet.getString("collection_base_type")
                        );
                    }
                }
        );
    }
}
