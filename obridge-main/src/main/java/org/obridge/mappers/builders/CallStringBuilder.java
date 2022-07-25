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

package org.obridge.mappers.builders;

import org.obridge.model.data.BindParam;
import org.obridge.model.data.Procedure;
import org.obridge.model.data.ProcedureArgument;
import org.obridge.util.TypeMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fkarsany on 2015.03.06..
 */
public class CallStringBuilder {

    private Procedure       procedure;
    private List<BindParam> bindParams;
    private int             bindParamId = 1;

    public CallStringBuilder(Procedure procedure) {
        this.procedure = procedure;
    }

    private void addLine(StringBuilder sb, String line) {
        sb.append("                \"" + line + "\" + \n");
    }

    private void addBindParam(ProcedureArgument pa, boolean inParam, boolean outParam) {
        bindParams.add(new BindParam(pa, bindParamId, inParam, outParam));
        bindParamId++;
    }

    public String build() {
        bindParams = new ArrayList<>();

        StringBuilder callString = new StringBuilder();

        addLine(callString, "");
        addLine(callString, "DECLARE ");

        generateBooleanInputParameters(callString);

        addLine(callString, "BEGIN ");

        generateCall(callString);

        generateBooleanOutputParameters(callString);

        addLine(callString, "END;");

        callString.append("\"\"");

        return callString.toString();

    }

    private void generateBooleanOutputParameters(StringBuilder callString) {
        for (ProcedureArgument pa : procedure.getArgumentList()) {
            if (pa.isOutputBooleanArgument()) {
                addLine(callString, "  :o" + pa.getArgumentName() + " := sys.diutil.bool_to_int(" + pa.getArgumentName() + ");");
                addBindParam(pa, false, true);
            }
        }
    }

    private void generateCall(StringBuilder callString) {
        generateOutReturnVariable(callString);

        if (procedure.getObjectName() != null && !"".equals(procedure.getObjectName())) {
            addLine(callString,
                    "  \\\"" + procedure.getOwner() + "\\\"" + "." + "\\\"" + procedure.getObjectName() + "\\\".\\\"" + procedure.getProcedureName() + "\\\"( ");
        } else {
            addLine(callString, "  \\\"" + procedure.getOwner() + "\\\"" + "." + "\\\"" + procedure.getProcedureName() + "\\\"( ");
        }

        generateParameters(callString);

        if ("FUNCTION".equals(procedure.getMethodType()) && TypeMapper.JAVA_BOOLEAN.equals(procedure.getReturnJavaType())) {
            addLine(callString, "  ) ");
        }

        addLine(callString, "   );");
    }

    private void generateParameters(StringBuilder callString) {
        boolean first = true;
        for (ProcedureArgument pa : procedure.getArgumentList()) {
            if (pa.getArgumentName() != null) {
                StringBuilder ln = new StringBuilder("  ");
                ln.append(!first ? " ," : "  ");
                if (pa.isJDBCTypeBoolean()) {
                    ln.append("\\\"" + pa.getArgumentName() + "\\\" => " + pa.getArgumentName());
                } else {
                    ln.append("\\\"" + pa.getArgumentName() + "\\\" => :" + pa.getArgumentName());
                    addBindParam(pa, pa.isInParam(), pa.isOutParam());
                }
                first = false;
                addLine(callString, ln.toString());
            }
        }
    }

    private void generateOutReturnVariable(StringBuilder callString) {
        if ("FUNCTION".equals(procedure.getMethodType())) {
            addLine(callString, "  :result := ");
            if (TypeMapper.JAVA_BOOLEAN.equals(procedure.getReturnJavaType())) {
                addLine(callString, "  sys.diutil.bool_to_int( ");
            }
            addBindParam(procedure.getArgumentList().get(0), false, true);
        }
    }

    private void generateBooleanInputParameters(StringBuilder callString) {
        // DECLARE BOOLEAN INPUT VARIABLES
        for (ProcedureArgument pa : procedure.getArgumentList()) {
            if (pa.isJDBCTypeBoolean() && pa.getArgumentName() != null) {
                StringBuilder ln = new StringBuilder("  " + pa.getArgumentName() + " BOOLEAN");
                if (pa.isInParam()) {
                    ln.append(" := sys.diutil.int_to_bool(:i" + pa.getArgumentName() + ")");
                    addBindParam(pa, true, false);
                }
                ln.append("; ");
                addLine(callString, ln.toString());
            }
        }
    }

    public List<BindParam> getBindParams() {
        return bindParams;
    }

}
