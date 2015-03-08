package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;

public class TestPackageTableOfTest1 {


    private BigDecimal functionReturn;
    private List<SampleTypeOne> listOf;

    public BigDecimal getFunctionReturn() {
        return this.functionReturn;
    }

    public void setFunctionReturn(BigDecimal functionReturn) {
        this.functionReturn = functionReturn;
    }

    public List<SampleTypeOne> getListOf() {
        return this.listOf;
    }

    public void setListOf(List<SampleTypeOne> listOf) {
        this.listOf = listOf;
    }


}