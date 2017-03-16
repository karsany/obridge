package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;
import org.obridge.test.database.context.NullityCheckCheckOutEmptyList;
import org.obridge.test.database.context.NullityCheckCheckOutNullList;

/**
 * Created by fkarsany on 2017. 03. 16..
 */
public class NullityCheckTest extends BaseTest {
    @Test
    public void checkOutNullList() throws Exception {
        try {
            NullityCheckCheckOutNullList nullityCheckCheckOutNullList = NullityCheck.checkOutNullList(ds);
            Assert.assertNull(nullityCheckCheckOutNullList.getListObject());
        } catch (NullPointerException e) {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void checkOutEmptyList() throws Exception {
        NullityCheckCheckOutEmptyList nullityCheckCheckOutEmptyList = NullityCheck.checkOutEmptyList(ds);
        Assert.assertNotNull(nullityCheckCheckOutEmptyList.getListObject());
        Assert.assertEquals(0, nullityCheckCheckOutEmptyList.getListObject().size());
    }

}