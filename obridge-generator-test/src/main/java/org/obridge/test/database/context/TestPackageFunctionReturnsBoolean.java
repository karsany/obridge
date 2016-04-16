package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class TestPackageFunctionReturnsBoolean {

    private Boolean functionReturn;
    private String param1;

    public Boolean getFunctionReturn() {
        return this.functionReturn;
    }

    public void setFunctionReturn(Boolean functionReturn) {
        this.functionReturn = functionReturn;
    }

    public String getParam1() {
        return this.param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }


}
