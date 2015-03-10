package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;

public class SimpleProceduresProcWithLists {


    private List<SampleTypeOne> p1;
    private List<SampleTypeTwo> p2;
    private List<SampleTypeTwo> p3;

    public List<SampleTypeOne> getP1() {
        return this.p1;
    }

    public void setP1(List<SampleTypeOne> p1) {
        this.p1 = p1;
    }

    public List<SampleTypeTwo> getP2() {
        return this.p2;
    }

    public void setP2(List<SampleTypeTwo> p2) {
        this.p2 = p2;
    }

    public List<SampleTypeTwo> getP3() {
        return this.p3;
    }

    public void setP3(List<SampleTypeTwo> p3) {
        this.p3 = p3;
    }


}
