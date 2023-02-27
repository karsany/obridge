package org.obridge.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
public class ResourceUtils {
    public static String load(String resourcePath) {
        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     new ClassPathResource(resourcePath).getInputStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new OBridgeException(e);
        }
    }
}
