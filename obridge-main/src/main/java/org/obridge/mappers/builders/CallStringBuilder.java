package org.obridge.mappers.builders;

import org.obridge.model.data.Procedure;
import org.obridge.model.data.ProcedureArgument;
import org.obridge.model.data.BindParam;

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

        for (ProcedureArgument pa : procedure.getArgumentList()) {
            if (pa.getOracleType().equals("BOOLEAN")) {
                String ln = "  " + pa.getArgumentName() + " BOOLEAN";
                if (pa.isInParam()) {
                    ln += " := sys.diutil.int_to_bool(:i" + pa.getArgumentName() + ")";
                    addBindParam("i" + pa.getArgumentName(), pa, true, false);
                }
                ln += "; ";
                addLine(callString, ln);
            }
        }

        addLine(callString, "BEGIN ");

        if ("FUNCTION".equals(procedure.getMethodType())) {
            addLine(callString, "  :result := ");
            addBindParam("result", procedure.getArgumentList().get(0), false, true);
        }

        addLine(callString, "  " + procedure.getObjectName() + "." + procedure.getProcedureName() + "( ");

        boolean first = true;
        for (ProcedureArgument pa : procedure.getArgumentList()) {
            if (pa.getArgumentName() != null) {
                String ln = "  ";
                ln += !first ? " ," : "  ";
                if (pa.getOracleType().equals("BOOLEAN")) {
                    ln += pa.getArgumentName() + " => " + pa.getArgumentName();
                } else {
                    ln += pa.getArgumentName() + " => :" + pa.getArgumentName();
                    addBindParam(pa.getArgumentName(), pa, pa.isInParam(), pa.isOutParam());
                }
                first = false;
                addLine(callString, ln);
            }
        }


        addLine(callString, "   );");

        for (ProcedureArgument pa : procedure.getArgumentList()) {
            if (pa.getOracleType().equals("BOOLEAN") && pa.isOutParam()) {
                addLine(callString, "  :o" + pa.getArgumentName() + " := sys.diutil.bool_to_int(" + pa.getArgumentName() + ");");
                addBindParam("o" + pa.getArgumentName(), pa, false, true);
            }
        }


        addLine(callString, "END;");

        callString.append("\"\"");

        return callString.toString();

    }

    public List<BindParam> getBindParams() {
        return bindParams;
    }


}
