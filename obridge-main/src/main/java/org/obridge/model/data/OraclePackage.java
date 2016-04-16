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

import org.obridge.util.StringHelper;

import java.util.List;

/**
 * Created by fkarsany on 2015.01.05..
 */
public class OraclePackage {

    private String name;
    private List<Procedure> procedureList;
    private String javaPackageName;
    private String contextPackage;
    private String converterPackage;
    private String objectPackage;

    public List<Procedure> getProcedureList() {
        return procedureList;
    }

    public void setProcedureList(List<Procedure> procedureList) {
        this.procedureList = procedureList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJavaClassName() {
        return StringHelper.toCamelCase(name);
    }

    public String getJavaPackageName() {
        return javaPackageName;
    }

    public void setJavaPackageName(String javaPackageName) {
        this.javaPackageName = javaPackageName;
    }

    public String getContextPackage() {
        return contextPackage;
    }

    public void setContextPackage(String contextPackage) {
        this.contextPackage = contextPackage;
    }

    public String getConverterPackage() {
        return converterPackage;
    }

    public void setConverterPackage(String converterPackage) {
        this.converterPackage = converterPackage;
    }

    public String getObjectPackage() {
        return objectPackage;
    }

    public void setObjectPackage(String objectPackage) {
        this.objectPackage = objectPackage;
    }
}
