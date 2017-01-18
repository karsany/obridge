package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;
import org.obridge.test.database.context.BlobTestTestcase1;
import org.obridge.test.database.context.BlobTestTestcase2;
import org.obridge.test.database.objects.TpBlobTest;

/**
 * Created by fkarsany on 2017. 01. 18..
 */
public class BlobTestTest extends BaseTest {

    @Test
    public void testcase1() throws Exception {
        BlobTestTestcase1 blobTestTestcase1 = BlobTest.testcase1(new byte[]{'a', 'b', 'c'}, ds);
        String str = new String(blobTestTestcase1.getTpBlb().getThisIsABlob());
        Assert.assertEquals("abchelo", str);
        Assert.assertEquals(7, blobTestTestcase1.getTpBlb().getBlobSize().intValue());

    }

    @Test
    public void testcase2() {
        TpBlobTest tpBlb = new TpBlobTest();
        tpBlb.setThisIsABlob(new byte[]{'a', 'b', 'c'});
        BlobTestTestcase2 blobTestTestcase2 = BlobTest.testcase2(tpBlb, ds);

        String str = new String(blobTestTestcase2.getBlb());
        Assert.assertEquals("abchelo", str);
        Assert.assertEquals(7, blobTestTestcase2.getSiz().intValue());


    }
}