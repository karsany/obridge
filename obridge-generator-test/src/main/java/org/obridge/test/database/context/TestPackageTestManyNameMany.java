package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class TestPackageTestManyNameMany {

    private List<SampleTypeTwo> tpGroup;
    private List<SampleTypeTwo> tpList;

    public List<SampleTypeTwo> getTpGroup() {
        return this.tpGroup;
    }

    public void setTpGroup(List<SampleTypeTwo> tpGroup) {
        this.tpGroup = tpGroup;
    }

    public List<SampleTypeTwo> getTpList() {
        return this.tpList;
    }

    public void setTpList(List<SampleTypeTwo> tpList) {
        this.tpList = tpList;
    }


}
