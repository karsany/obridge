package org.obridge.test.database.packages;

import org.junit.Assert;
import org.junit.Test;
import org.obridge.test.BaseTest;
import org.obridge.test.database.context.NullityCheckCheckOutEmptyList;
import org.obridge.test.database.context.NullityCheckCheckOutEmptyObject;
import org.obridge.test.database.context.NullityCheckCheckOutNullList;
import org.obridge.test.database.context.NullityCheckCheckOutNullObject;

/**
 * Created by fkarsany on 2017. 03. 16..
 */
public class NullityCheckTest extends BaseTest {
    @Test
    public void checkOutNullList() throws Exception {
        try {
            NullityCheckCheckOutNullList result = NullityCheck.checkOutNullList(ds);
            Assert.assertNull(result.getListObject());
        } catch (NullPointerException e) {
            Assert.assertFalse(true);
        }
    }

    @Test
    public void checkOutEmptyList() throws Exception {
        NullityCheckCheckOutEmptyList result = NullityCheck.checkOutEmptyList(ds);
        Assert.assertNotNull(result.getListObject());
        Assert.assertEquals(0, result.getListObject().size());
    }

    @Test
    public void checkOutNullObject() throws Exception {
        NullityCheckCheckOutNullObject result = NullityCheck.checkOutNullObject(ds);
        Assert.assertNull(result.getSampleObject());
    }

    @Test
    public void checkOutEmptyObject() throws Exception {
        NullityCheckCheckOutEmptyObject result = NullityCheck.checkOutEmptyObject(ds);
        Assert.assertNotNull(result.getSampleObject());
    }
}