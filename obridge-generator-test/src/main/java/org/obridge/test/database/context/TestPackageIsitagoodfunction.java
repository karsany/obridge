package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;

public class TestPackageIsitagoodfunction {


    private Boolean functionReturn;
    private SampleTypeOne param1;

    public Boolean getFunctionReturn() {
        return this.functionReturn;
    }

    public void setFunctionReturn(Boolean functionReturn) {
        this.functionReturn = functionReturn;
    }

    public SampleTypeOne getParam1() {
        return this.param1;
    }

    public void setParam1(SampleTypeOne param1) {
        this.param1 = param1;
    }


}
