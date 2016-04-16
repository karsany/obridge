package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class TestPackageSumList {

    private BigDecimal functionReturn;
    private List<Integer> list;

    public BigDecimal getFunctionReturn() {
        return this.functionReturn;
    }

    public void setFunctionReturn(BigDecimal functionReturn) {
        this.functionReturn = functionReturn;
    }

    public List<Integer> getList() {
        return this.list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }


}
