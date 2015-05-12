package org.obridge.mappers.builders;

import org.obridge.model.data.ProcedureArgument;
import org.obridge.util.MustacheRunner;

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

        if (pa.getDataType().equals("OBJECT")) {
            return MustacheRunner.build("sniplets/OBJECT-GET.mustache", pa);
        }

        if (pa.isPrimitiveList()) {
            return MustacheRunner.build("sniplets/LIST-PRIMITIVE-GET.mustache", pa);
        }

        if (pa.getDataType().equals("TABLE")) {
            return MustacheRunner.build("sniplets/LIST-GET.mustache", pa);
        }

        if (pa.getJDBCType().equals("BOOLEAN")) {
            return MustacheRunner.build("sniplets/BOOLEAN-GET.mustache", pa);
        }

        if (pa.getJavaDataType().equals("Integer")) {
            return MustacheRunner.build("sniplets/INTEGER-GET.mustache", pa);
        }

        return MustacheRunner.build("sniplets/DEFAULT-GET.mustache", pa);

    }
}
