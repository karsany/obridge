package org.obridge.mappers.builders;

import org.obridge.model.data.BindParam;
import org.obridge.model.data.Procedure;
import org.obridge.model.data.ProcedureArgument;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fkarsany on 2015.03.06..
 */
public class CallStringBuilder {

    private Procedure procedure;
    private List<BindParam> bindParams;
    private int bindParamId = 1;

    public CallStringBuilder(Procedure procedure) {
        this.procedure = procedure;
    }

    private void addLine(StringBuilder sb, String line) {
        sb.append("                \"" + line + "\" + \n");
    }

    private void addBindParam(String paramName, ProcedureArgument pa, boolean inParam, boolean outParam) {
        bindParams.add(new BindParam(pa, bindParamId, paramName, inParam, outParam));
        bindParamId++;
    }

    public String build() {
        bindParams = new ArrayList<BindParam>();

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
            if (pa.getArgumentName() != null) {
                if (pa.getOracleType().equals("BOOLEAN") && pa.isOutParam()) {
                    addLine(callString, "  :o" + pa.getArgumentName() + " := sys.diutil.bool_to_int(" + pa.getArgumentName() + ");");
                    addBindParam("o" + pa.getArgumentName(), pa, false, true);
                }
            }
        }
    }

    private void generateCall(StringBuilder callString) {
        generateOutReturnVariable(callString);

        addLine(callString, "  \\\"" + procedure.getObjectName() + "\\\".\\\"" + procedure.getProcedureName() + "\\\"( ");

        generateParameters(callString);


        if ("FUNCTION".equals(procedure.getMethodType())) {
            if (procedure.getReturnJavaType().equals("Boolean")) {
                addLine(callString, "  ) ");
            }
        }

        addLine(callString, "   );");
    }

    private void generateParameters(StringBuilder callString) {
        boolean first = true;
        for (ProcedureArgument pa : procedure.getArgumentList()) {
            if (pa.getArgumentName() != null) {
                String ln = "  ";
                ln += !first ? " ," : "  ";
                if (pa.getOracleType().equals("BOOLEAN")) {
                    ln += "\\\"" + pa.getArgumentName() + "\\\" => " + pa.getArgumentName();
                } else {
                    ln += "\\\"" + pa.getArgumentName() + "\\\" => :" + pa.getArgumentName();
                    addBindParam(pa.getArgumentName(), pa, pa.isInParam(), pa.isOutParam());
                }
                first = false;
                addLine(callString, ln);
            }
        }
    }

    private void generateOutReturnVariable(StringBuilder callString) {
        if ("FUNCTION".equals(procedure.getMethodType())) {
            addLine(callString, "  :result := ");
            if (procedure.getReturnJavaType().equals("Boolean")) {
                addLine(callString, "  sys.diutil.bool_to_int( ");
            }
            addBindParam("result", procedure.getArgumentList().get(0), false, true);
        }
    }

    private void generateBooleanInputParameters(StringBuilder callString) {
        // DECLARE BOOLEAN INPUT VARIABLES
        for (ProcedureArgument pa : procedure.getArgumentList()) {
            if (pa.getOracleType().equals("BOOLEAN") && pa.getArgumentName() != null) {
                String ln = "  " + pa.getArgumentName() + " BOOLEAN";
                if (pa.isInParam()) {
                    ln += " := sys.diutil.int_to_bool(:i" + pa.getArgumentName() + ")";
                    addBindParam("i" + pa.getArgumentName(), pa, true, false);
                }
                ln += "; ";
                addLine(callString, ln);
            }
        }
    }

    public List<BindParam> getBindParams() {
        return bindParams;
    }


}
