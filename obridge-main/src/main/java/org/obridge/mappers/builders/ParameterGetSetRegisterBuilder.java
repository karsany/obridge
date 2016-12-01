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

        if (TypeMapper.JAVA_BYTEARRAY.equals(pa.getJavaDataType())) {
            return MustacheRunner.build("sniplets/RAW-GET.mustache", pa);
        }

        return MustacheRunner.build("sniplets/DEFAULT-GET.mustache", pa);

    }
}
