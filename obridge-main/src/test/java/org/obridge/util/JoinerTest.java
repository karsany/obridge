package org.obridge.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author fkarsany
 */
public class JoinerTest {

    public JoinerTest() {
    }

    @Test
    public void testJoin() {
        Assert.assertEquals("a,b,c", Joiner.on(",").skipNulls().join(Arrays.asList("a", "b", "c")));
        Assert.assertEquals("a , b , c", Joiner.on(" , ").skipNulls().join(Arrays.asList("a", "b", "c")));
        Assert.assertEquals("a , c", Joiner.on(" , ").skipNulls().join(Arrays.asList("a", null, "c")));
        Assert.assertEquals("a ,  , c", Joiner.on(" , ").join(Arrays.asList("a", "", "c")));
        Assert.assertEquals("a,,c", Joiner.on(",").join(Arrays.asList("a", "", "c")));
        Assert.assertEquals("a", Joiner.on(",").join(Arrays.asList("a")));
        Assert.assertEquals("a", Joiner.on(",").skipNulls().join(Arrays.asList("a")));
        Assert.assertEquals("a", Joiner.on(",").skipNulls().join(Arrays.asList(null, "a")));
        Assert.assertEquals(",a", Joiner.on(",").join(Arrays.asList("", "a")));
        Assert.assertEquals("a", Joiner.on(",").skipNulls().join(Arrays.asList("a", null)));
        Assert.assertEquals("a,", Joiner.on(",").join(Arrays.asList("a", "")));
    }

}
