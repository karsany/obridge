package org.obridge.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringHelperTest {

    @Test
    public void shouldConvertToPascalCase() {
        Assertions.assertEquals("HelloWorld", StringHelper.toPascalCase("hello_world"));
        Assertions.assertEquals("", StringHelper.toPascalCase(null));
    }

    @Test
    public void shouldConvertToCamelCase() {
        Assertions.assertEquals("helloWorld", StringHelper.toCamelCase("hello_world"));
        Assertions.assertEquals("", StringHelper.toCamelCase(null));
    }

    @Test
    public void testToOracleName() {
        Assertions.assertEquals("HELLO_WORLD", StringHelper.toCapitalizedSnakeCase("hello_world"));
        Assertions.assertEquals("HELLO_WORLD", StringHelper.toCapitalizedSnakeCase("HelloWorld"));
        Assertions.assertEquals("HELLO_WORLD", StringHelper.toCapitalizedSnakeCase("helloWorld"));
    }

    @Test
    public void unJavaKeyword() {
        Assertions.assertEquals("pASSERT", StringHelper.unJavaKeyword("ASSERT"));
    }


}
