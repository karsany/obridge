package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class SimpleProceduresRefcursorTest {


    private ResultSet refc;

    public ResultSet getRefc() {
        return this.refc;
    }

    public void setRefc(ResultSet refc) {
        this.refc = refc;
    }


}
