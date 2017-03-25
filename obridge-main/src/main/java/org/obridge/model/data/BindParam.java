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

/**
 * Created by fkarsany on 2015.03.06..
 */
public class BindParam {

    private ProcedureArgument procedureArgument;
    private int sequenceNumber;
    private boolean inParam;
    private boolean outParam;

    public BindParam(ProcedureArgument procedureArgument, int sequenceNumber, boolean inParam, boolean outParam) {
        this.procedureArgument = procedureArgument;
        this.sequenceNumber = sequenceNumber;
        this.inParam = inParam;
        this.outParam = outParam;
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

    public boolean isOutParam() {
        return outParam;
    }

}
