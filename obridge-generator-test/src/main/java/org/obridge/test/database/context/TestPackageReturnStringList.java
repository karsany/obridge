package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;

public class TestPackageReturnStringList {


    private List<String> functionReturn;

    public List<String> getFunctionReturn() {
        return this.functionReturn;
    }

    public void setFunctionReturn(List<String> functionReturn) {
        this.functionReturn = functionReturn;
    }


}
