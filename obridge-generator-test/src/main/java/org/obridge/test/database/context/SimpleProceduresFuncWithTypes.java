package org.obridge.test.database.context;

import org.obridge.test.database.objects.SampleTypeOne;
import org.obridge.test.database.objects.SampleTypeTwo;

import java.util.List;

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