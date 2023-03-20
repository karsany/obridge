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

package org.obridge.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.obridge.context.OBridgeConfiguration;
import org.obridge.model.data.TypeAttribute;
import org.obridge.model.dto.TypeIdDto;
import org.obridge.util.ResourceUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: fkarsany
 * Date: 2013.11.14.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class TypeDao {

    private final JdbcTemplate jdbcTemplate;

    public List<TypeIdDto> getTypeList(OBridgeConfiguration c) {
        assert c != null;

        String query = ResourceUtils.load("queries/GET_TYPENAME_BY_CODE.sql");
        if (c.getIncludes() != null) {
            query += " and (owner, type_name) IN (" + c.toFilterString() + ")";
        } else {
            query += "and owner = user";
        }

        return jdbcTemplate.query(query,
                (resultSet, i) -> new TypeIdDto(
                        resultSet.getString("owner"),
                        resultSet.getString("type_name")), "OBJECT");
    }

    public List<TypeAttribute> getTypeAttributes(TypeIdDto t) {
        RowMapper<TypeAttribute> typeAttributeRowMapper = (resultSet, i) -> new TypeAttribute(resultSet.getString("attr_name"),
                resultSet.getString("attr_type_name"),
                resultSet.getString("owner"),
                resultSet.getInt("attr_no"),
                resultSet.getInt("data_scale"),
                resultSet.getInt("multi_type"),
                resultSet.getString("typecode"),
                resultSet.getString("collection_base_type"));

        String load = ResourceUtils.load("queries/GET_TYPE_ATTRIBUTES.sql");
        return jdbcTemplate.query(load, typeAttributeRowMapper, t.getTypeName(), t.getOwner());
    }
}
