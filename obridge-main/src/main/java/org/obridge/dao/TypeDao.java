package org.obridge.dao;

import org.obridge.model.data.TypeAttribute;
import org.obridge.util.jdbc.JdbcTemplate;
import org.obridge.util.jdbc.RowMapper;

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
        return jdbcTemplate.queryForList("SELECT type_name FROM user_types WHERE typecode = 'OBJECT'");
    }

    public List<String> getEmbeddedTypeList() {
        return jdbcTemplate.queryForList("Select Distinct type_name || '_' || type_subname\n" +
                "  From user_arguments t\n" +
                " Where type_subname Is Not Null");
    }

    public List<TypeAttribute> getEmbeddedTypeAttributes(String typeName) {
        return jdbcTemplate.query("Select *\n" +
                        "  From (Select Distinct d.argument_name attr_name,\n" +
                        "                        d.data_type attr_type_name,\n" +
                        "                        d.position attr_no,\n" +
                        "                        nvl(d.data_scale, -1) data_scale,\n" +
                        "                        0 multi_type,\n" +
                        "                        'OBJECT' typecode,\n" +
                        "                        Null collection_base_type\n" +
                        "          From (Select t.*\n" +
                        "                  From user_arguments t\n" +
                        "                 Where t.type_name || '_' || t.type_subname = ?\n" +
                        "                   And rownum < 2) m\n" +
                        "          Left Join (Select *\n" +
                        "                      From user_arguments t\n" +
                        "                     Where data_level = 1) d\n" +
                        "            On m.object_name = d.object_name\n" +
                        "           And m.package_name = d.package_name\n" +
                        "           And nvl(m.overload, -1) = nvl(d.overload, -1))\n" +
                        " Order By attr_no\n",
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
                });
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
