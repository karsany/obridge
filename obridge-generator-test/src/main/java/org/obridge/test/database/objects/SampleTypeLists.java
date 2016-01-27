package org.obridge.test.database.objects;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import javax.annotation.Generated;

@Generated("org.obridge.generators.EntityObjectGenerator")
public class SampleTypeLists {


    private List<SampleTypeOne> list1;
    private List<SampleTypeTwo> list2;
    private List<SampleTypeTwo> list3;
    private List<String> list4;
    private List<Integer> list5;

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

    public List<String> getList4() {
        return this.list4;
    }

    public void setList4(List<String> list4) {
        this.list4 = list4;
    }

    public List<Integer> getList5() {
        return this.list5;
    }

    public void setList5(List<Integer> list5) {
        this.list5 = list5;
    }


}
