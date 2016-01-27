package org.obridge.test.database.objects;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import javax.annotation.Generated;

@Generated("org.obridge.generators.EntityObjectGenerator")
public class SampleTypeTwo {


    private String field1;
    private SampleTypeOne field2;
    private List<SampleTypeOne> field3;

    public String getField1() {
        return this.field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public SampleTypeOne getField2() {
        return this.field2;
    }

    public void setField2(SampleTypeOne field2) {
        this.field2 = field2;
    }

    public List<SampleTypeOne> getField3() {
        return this.field3;
    }

    public void setField3(List<SampleTypeOne> field3) {
        this.field3 = field3;
    }


}
