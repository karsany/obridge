package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class Execfunction {


    private String functionReturn;
    private BigDecimal pnumber;
    private String pintext;
    private String pouttext;

    public String getFunctionReturn() {
        return this.functionReturn;
    }

    public void setFunctionReturn(String functionReturn) {
        this.functionReturn = functionReturn;
    }

    public BigDecimal getPnumber() {
        return this.pnumber;
    }

    public void setPnumber(BigDecimal pnumber) {
        this.pnumber = pnumber;
    }

    public String getPintext() {
        return this.pintext;
    }

    public void setPintext(String pintext) {
        this.pintext = pintext;
    }

    public String getPouttext() {
        return this.pouttext;
    }

    public void setPouttext(String pouttext) {
        this.pouttext = pouttext;
    }


}
