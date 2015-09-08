package org.obridge.model.data;

import org.obridge.mappers.builders.CallStringBuilder;
import org.obridge.util.OBridgeException;
import org.obridge.util.StringHelper;
import org.obridge.util.TypeMapper;

import java.util.List;

/**
 * User: fkarsany Date: 2013.11.18.
 */
public class Procedure {

    private String objectName;
    private String procedureName;
    private String overload;
    private String methodType;
    private List<ProcedureArgument> argumentList;
    private List<BindParam> bindParams = null;
    private String callString;

    public Procedure(String objectName, String procedureName, String overload, String methodType, List<ProcedureArgument> argumentList) {
        this.objectName = objectName;
        this.procedureName = procedureName;
        this.overload = overload;
        this.methodType = methodType;
        this.argumentList = argumentList;
        initBindParams();
    }

    public Procedure(String objectName, String procedureName, String overload, String methodType) {
        this.objectName = objectName;
        this.procedureName = procedureName;
        this.overload = overload;
        this.methodType = methodType;
        initBindParams();
    }

    public void initBindParams() {
        CallStringBuilder callStringBuilder = new CallStringBuilder(this);
        this.callString = callStringBuilder.build();
        this.bindParams = callStringBuilder.getBindParams();
    }

    public List<ProcedureArgument> getArgumentList() {

        return argumentList;
    }

    public void setArgumentList(List<ProcedureArgument> argumentList) {
        this.argumentList = argumentList;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getObjectName() {

        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getOverload() {
        return overload;
    }

    public void setOverload(String overload) {
        this.overload = overload;
    }

    public String getJavaProcedureName() {
        String r = StringHelper.toCamelCaseSmallBegin(this.procedureName + "_" + this.overload);
        return StringHelper.unJavaKeyword(r);
    }

    public String getStoredProcedureClassName() {
        return StringHelper.toCamelCase(this.getObjectName() + "_" + this.getProcedureName() + "_" + this.getOverload());
    }

    public String getReturnJavaType() {
        if (!"FUNCTION".equals(methodType)) {
            throw new OBridgeException("Only for functions.");
        }
        if (hasArguments()) {
            return argumentList.get(0).getJavaDataType();
        } else {
            return "void";
        }
    }

    public boolean hasArguments() {
        return this.argumentList != null && !this.argumentList.isEmpty();
    }

    public String getCallString() {
        return this.callString;
    }

    public void setCallString(String callString) {
        this.callString = callString;
    }

    public List<BindParam> getBindParams() {
        return bindParams;
    }

    public void setBindParams(List<BindParam> bindParams) {
        this.bindParams = bindParams;
    }

    public boolean hasResultSetParam() {
        for (ProcedureArgument pa : this.argumentList) {
            if (TypeMapper.JAVA_RESULTSET.equals(pa.getJavaDataType())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Procedure{"
                + "objectName='" + objectName + '\''
                + ", procedureName='" + procedureName + '\''
                + ", overload='" + overload + '\''
                + ", methodType='" + methodType + '\''
                + ", argumentList=" + argumentList
                + '}';
    }
}
