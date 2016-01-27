package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class TestPackageBooleanTest2 {


    private BigDecimal n;
    private Boolean boolIn;
    private Boolean boolOut;
    private Boolean boolInout;

    public BigDecimal getN() {
        return this.n;
    }

    public void setN(BigDecimal n) {
        this.n = n;
    }

    public Boolean getBoolIn() {
        return this.boolIn;
    }

    public void setBoolIn(Boolean boolIn) {
        this.boolIn = boolIn;
    }

    public Boolean getBoolOut() {
        return this.boolOut;
    }

    public void setBoolOut(Boolean boolOut) {
        this.boolOut = boolOut;
    }

    public Boolean getBoolInout() {
        return this.boolInout;
    }

    public void setBoolInout(Boolean boolInout) {
        this.boolInout = boolInout;
    }


}
