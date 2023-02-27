package org.obridge.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceUtilsTest {

    @Test
    void whenValidPath_shouldLoad() {
        String data = ResourceUtils.load("queries/GET_PROCEDURE_ARGUMENTS.sql");

        assertEquals("SELECT *\n" +
                "FROM DUAL;", data);
    }
    @Test
    void whenNotValidPath_shouldThrowRuntimeException() {
        assertThrows(RuntimeException.class,() -> {
            ResourceUtils.load("queries/NOT_VALID.sql");
        });
    }
}
