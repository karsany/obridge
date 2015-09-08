package org.obridge.mappers.builders;

import org.obridge.model.data.ProcedureArgument;
import org.obridge.util.MustacheRunner;
import org.obridge.util.TypeMapper;

/**
 * Created by fkarsany on 2015.03.06..
 */
public class ParameterGetSetRegisterBuilder {
    private ProcedureArgument pa;

    public ParameterGetSetRegisterBuilder(ProcedureArgument pa) {
        this.pa = pa;
    }

    public String setParameter(int sequenceNumber) {

        pa.setSequenceNumber(sequenceNumber);

        if (TypeMapper.ORACLE_OBJECT.equals(pa.getDataType())) {
            return MustacheRunner.build("sniplets/OBJECT-GET.mustache", pa);
        }

        if (pa.isPrimitiveList()) {
            return MustacheRunner.build("sniplets/LIST-PRIMITIVE-GET.mustache", pa);
        }

        if (TypeMapper.ORACLE_TABLE.equals(pa.getDataType())) {
            return MustacheRunner.build("sniplets/LIST-GET.mustache", pa);
        }

        if (TypeMapper.JDBC_BOOLEAN.equals(pa.getJDBCType())) {
            return MustacheRunner.build("sniplets/BOOLEAN-GET.mustache", pa);
        }

        if (TypeMapper.JAVA_INTEGER.equals(pa.getJavaDataType())) {
            return MustacheRunner.build("sniplets/INTEGER-GET.mustache", pa);
        }

        return MustacheRunner.build("sniplets/DEFAULT-GET.mustache", pa);

    }
}
