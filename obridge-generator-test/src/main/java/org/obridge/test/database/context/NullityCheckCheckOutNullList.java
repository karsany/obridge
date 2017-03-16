package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class NullityCheckCheckOutNullList {

    private List<SampleTypeOne> listObject;

    public List<SampleTypeOne> getListObject() {
        return this.listObject;
    }

    public void setListObject(List<SampleTypeOne> listObject) {
        this.listObject = listObject;
    }


}
