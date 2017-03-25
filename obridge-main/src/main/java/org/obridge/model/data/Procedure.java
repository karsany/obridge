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

package org.obridge.model.data;

import org.obridge.mappers.builders.CallStringBuilder;
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

    private Procedure() {
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
        return argumentList.get(0).getJavaDataType();
    }

    public String getCallString() {
        return this.callString;
    }


    public List<BindParam> getBindParams() {
        return bindParams;
    }


    public boolean hasResultSetParam() {
        for (ProcedureArgument pa : this.argumentList) {
            if (TypeMapper.JAVA_RESULTSET.equals(pa.getJavaDataType())) {
                return true;
            }
        }
        return false;
    }

    public static class Builder {
        Procedure p = new Procedure();

        public Builder objectName(String objectName) {
            this.p.setObjectName(objectName);
            return this;
        }

        public Builder procedureName(String procedureName) {
            this.p.setProcedureName(procedureName);
            return this;
        }

        public Builder overload(String overload) {
            this.p.setOverload(overload);
            return this;
        }

        public Builder methodType(String methodType) {
            this.p.setMethodType(methodType);
            return this;
        }

        public Builder argumentList(List<ProcedureArgument> argumentList) {
            this.p.setArgumentList(argumentList);
            return this;
        }

        public Procedure build() {
            initBindParams();
            return p;
        }

        private void initBindParams() {
            CallStringBuilder callStringBuilder = new CallStringBuilder(p);
            p.callString = callStringBuilder.build();
            p.bindParams = callStringBuilder.getBindParams();
        }

    }

}
