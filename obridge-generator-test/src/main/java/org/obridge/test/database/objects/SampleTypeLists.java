package org.obridge.test.database.objects;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;

public class SampleTypeLists {


    private List<SampleTypeOne> list1;
    private List<SampleTypeTwo> list2;
    private List<SampleTypeTwo> list3;

    public List<SampleTypeOne> getList1() {
        return this.list1;
    }

    public void setList1(List<SampleTypeOne> list1) {
        this.list1 = list1;
    }

    public List<SampleTypeTwo> getList2() {
        return this.list2;
    }

    public void setList2(List<SampleTypeTwo> list2) {
        this.list2 = list2;
    }

    public List<SampleTypeTwo> getList3() {
        return this.list3;
    }

    public void setList3(List<SampleTypeTwo> list3) {
        this.list3 = list3;
    }


}
