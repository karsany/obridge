package org.obridge.test.database.objects;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.math.BigDecimal;
import javax.annotation.Generated;

@Generated("org.obridge.generators.EntityObjectGenerator")
public class TpBlobTest {

    private byte[] thisIsABlob;
    private Integer blobSize;

    public byte[] getThisIsABlob() {
        return this.thisIsABlob;
    }

    public void setThisIsABlob(byte[] thisIsABlob) {
        this.thisIsABlob = thisIsABlob;
    }

    public Integer getBlobSize() {
        return this.blobSize;
    }

    public void setBlobSize(Integer blobSize) {
        this.blobSize = blobSize;
    }


}
