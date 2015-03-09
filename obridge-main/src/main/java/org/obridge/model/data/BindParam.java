package org.obridge.model.data;

import org.obridge.model.data.ProcedureArgument;

/**
 * Created by fkarsany on 2015.03.06..
 */
public class BindParam {

    private ProcedureArgument procedureArgument;
    private int sequenceNumber;
    private String paramName;
    private boolean inParam;
    private boolean outParam;

    public BindParam(ProcedureArgument procedureArgument, int sequenceNumber, String paramName, boolean inParam, boolean outParam) {
        this.procedureArgument = procedureArgument;
        this.sequenceNumber = sequenceNumber;
        this.paramName = paramName;
        this.inParam = inParam;
        this.outParam = outParam;
    }

    public ProcedureArgument getProcedureArgument() {
        return procedureArgument;
    }

    public void setProcedureArgument(ProcedureArgument procedureArgument) {
        this.procedureArgument = procedureArgument;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamSet() {
        return procedureArgument.getParamSet(sequenceNumber);
    }

    public String getParamGet() {
        return procedureArgument.getParamGet(sequenceNumber);
    }

    public String getRegOutput() {
        return procedureArgument.getRegOutput(sequenceNumber);
    }

    public boolean isInParam() {
        return inParam;
    }

    public void setInParam(boolean inParam) {
        this.inParam = inParam;
    }

    public boolean isOutParam() {
        return outParam;
    }

    public void setOutParam(boolean outParam) {
        this.outParam = outParam;
    }

    @Override
    public String toString() {
        return "BindParam{" +
                "procedureArgument=" + procedureArgument +
                ", sequenceNumber=" + sequenceNumber +
                ", paramName='" + paramName + '\'' +
                ", inParam=" + inParam +
                ", outParam=" + outParam +
                '}';
    }
}
