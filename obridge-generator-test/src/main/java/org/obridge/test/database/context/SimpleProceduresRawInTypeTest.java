package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class SimpleProceduresRawInTypeTest {

    private SampleTypeOne tt;

    public SampleTypeOne getTt() {
        return this.tt;
    }

    public void setTt(SampleTypeOne tt) {
        this.tt = tt;
    }


}
