package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;

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