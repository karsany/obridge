package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class TestPackageGetSysdate2 {

    private Date sysdate;

    public Date getSysdate() {
        return this.sysdate;
    }

    public void setSysdate(Date sysdate) {
        this.sysdate = sysdate;
    }


}
