package org.obridge.test.database.context;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import java.sql.ResultSet;
import org.obridge.test.database.objects.*;
import javax.annotation.Generated;

@Generated("org.obridge.generators.ProcedureContextGenerator")
public class BlobTestTestcase1 {

    private byte[] blb;
    private TpBlobTest tpBlb;

    public byte[] getBlb() {
        return this.blb;
    }

    public void setBlb(byte[] blb) {
        this.blb = blb;
    }

    public TpBlobTest getTpBlb() {
        return this.tpBlb;
    }

    public void setTpBlb(TpBlobTest tpBlb) {
        this.tpBlb = tpBlb;
    }


}
