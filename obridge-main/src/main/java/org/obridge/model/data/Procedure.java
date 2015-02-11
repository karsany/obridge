package org.obridge.model.data;

import com.google.common.base.Joiner;
import org.obridge.util.FuncUtils;
import org.obridge.util.StringHelper;

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

    public Procedure(String objectName, String procedureName, String overload, String methodType, List<ProcedureArgument> argumentList) {
        this.objectName = objectName;
        this.procedureName = procedureName;
        this.overload = overload;
        this.methodType = methodType;
        this.argumentList = argumentList;
    }

    public Procedure(String objectName, String procedureName, String overload, String methodType) {
        this.objectName = objectName;
        this.procedureName = procedureName;
        this.overload = overload;
        this.methodType = methodType;
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
        if (!methodType.equals("FUNCTION")) {
            throw new RuntimeException("Csak fuggvenyek eseteben ertelmezett.");
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
        StringBuilder callString = new StringBuilder();
        callString.append("{ CALL ");
        if ("FUNCTION".equals(this.getMethodType())) {
            callString.append("? := ");
        }
        callString.append(this.getObjectName() + "." + this.getProcedureName() + "( ");
        List<String> argumentNames = FuncUtils.pluck("argumentParameterName", String.class, this.argumentList);

        callString.append(Joiner.on(" , ").skipNulls().join(argumentNames));
        callString.append(" ) }");

        return callString.toString();
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
