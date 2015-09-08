package org.obridge.mappers;


import org.obridge.model.data.Procedure;
import org.obridge.model.data.ProcedureArgument;
import org.obridge.model.data.TypeAttribute;
import org.obridge.model.generator.Pojo;
import org.obridge.model.generator.PojoField;
import org.obridge.util.StringHelper;

import java.util.ArrayList;
import java.util.List;

public final class PojoMapper {

    private PojoMapper() {
    }

    public static Pojo typeToPojo(String typeName, List<TypeAttribute> typeAttributes) {
        Pojo p = new Pojo();
        p.setClassName(StringHelper.toCamelCase(typeName));

        p.setFields(new ArrayList<PojoField>());

        for (TypeAttribute t : typeAttributes) {
            PojoField field = new PojoField();
            field.setFieldName(StringHelper.toCamelCaseSmallBegin(t.getAttrName()));
            field.setFieldType(t.getJavaDataType());
            field.setReadonly(false);
            p.getFields().add(field);
        }

        p.setImports(new ArrayList<String>());

        p.getImports().add("java.sql.Timestamp");
        p.getImports().add("java.sql.Date");
        p.getImports().add("java.util.List");
        p.getImports().add("java.math.BigDecimal");

        return p;

    }

    public static Pojo procedureToPojo(Procedure procedure) {
        Pojo p = new Pojo();
        p.setClassName(procedure.getStoredProcedureClassName());

        p.setFields(new ArrayList<PojoField>());

        for (ProcedureArgument t : procedure.getArgumentList()) {
            PojoField field = new PojoField();
            field.setFieldName(t.getJavaPropertyName());
            field.setFieldType(t.getJavaDataType());
            p.getFields().add(field);
        }

        p.setImports(new ArrayList<String>());

        p.getImports().add("java.sql.Timestamp");
        p.getImports().add("java.sql.Date");
        p.getImports().add("java.util.List");
        p.getImports().add("java.math.BigDecimal");
        p.getImports().add("java.sql.ResultSet");

        return p;

    }

}
