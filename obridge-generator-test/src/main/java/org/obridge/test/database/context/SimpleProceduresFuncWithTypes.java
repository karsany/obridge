package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class SimpleProceduresFuncWithTypes {


    private SampleTypeOne param1;
    private List<SampleTypeOne> paramHello;
    private SampleTypeTwo paramTwo;

    public SampleTypeOne getParam1() {
        return this.param1;
    }

    public void setParam1(SampleTypeOne param1) {
        this.param1 = param1;
    }

    public List<SampleTypeOne> getParamHello() {
        return this.paramHello;
    }

    public void setParamHello(List<SampleTypeOne> paramHello) {
        this.paramHello = paramHello;
    }

    public SampleTypeTwo getParamTwo() {
        return this.paramTwo;
    }

    public void setParamTwo(SampleTypeTwo paramTwo) {
        this.paramTwo = paramTwo;
    }


}
