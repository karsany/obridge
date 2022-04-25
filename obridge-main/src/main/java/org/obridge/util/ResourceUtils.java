package org.obridge.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ResourceUtils {
    public static String load(String resourcePath) {
        InputStream is = ResourceUtils.class.getResourceAsStream("/" + resourcePath);
        String      s  = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        return s;
    }
}
