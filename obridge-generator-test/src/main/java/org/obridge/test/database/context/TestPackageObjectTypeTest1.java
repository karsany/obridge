package org.obridge.test.database.context;

import org.obridge.test.database.objects.SampleTypeOne;

import java.math.BigDecimal;

public class TestPackageObjectTypeTest1 {


    private BigDecimal functionReturn;
    private SampleTypeOne objectType;

    public BigDecimal getFunctionReturn() {
        return this.functionReturn;
    }

    public void setFunctionReturn(BigDecimal functionReturn) {
        this.functionReturn = functionReturn;
    }

    public SampleTypeOne getObjectType() {
        return this.objectType;
    }

    public void setObjectType(SampleTypeOne objectType) {
        this.objectType = objectType;
    }


}