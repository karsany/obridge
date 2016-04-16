package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class TestPackageCombinedTypesTest1 {

    private SampleTypeOne objectType;
    private List<SampleTypeOne> listOf;

    public SampleTypeOne getObjectType() {
        return this.objectType;
    }

    public void setObjectType(SampleTypeOne objectType) {
        this.objectType = objectType;
    }

    public List<SampleTypeOne> getListOf() {
        return this.listOf;
    }

    public void setListOf(List<SampleTypeOne> listOf) {
        this.listOf = listOf;
    }


}
