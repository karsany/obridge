/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Ferenc Karsany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package org.obridge.context;

import lombok.NonNull;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OBridge Configuration package.
 *
 * @author Ferenc Karsany (karsany@karsany.hu)
 * @version $Id$
 * @since 1.0
 */
@Value
@Validated
@ConfigurationProperties(prefix = "obridge")
public class OBridgeConfiguration {

    /**
     * Root of the java sources.
     */
    @NonNull
    String sourceRoot;

    /**
     * Root package structure. Eg: hu.obridge.test.
     */
    @NonNull
    String rootPackageName;

    /**
     * Flag for generate nested types. Default true.
     */
    @NonNull
    boolean generateNestedTypes;
    Packages packages;
    Logging logging;


    /**
     * Description for obridge.includes. If empty, include all.
     */
    List<DbObject> includes;

    public String toFilterString() {
        return this.includes.stream().map(DbObject::toSQL).collect(Collectors.joining(" UNION ALL "));
    }

    @Value
    public static class DbObject {

        /**
         * Owner of the resource.
         */
        String owner;

        /**
         * Name of the resource.
         */
        String name;

        public String toSQL() {
            assert this.owner != null;
            assert this.name != null;

            return "SELECT '"
                    .concat(this.owner)
                    .concat("', '")
                    .concat(this.name)
                    .concat("' FROM dual ");
        }

    }

    @Value
    public static class Packages {

        /**
         * Description for obridge.packages.entityObjects. Default: objects.
         */
        String entityObjects;
        /**
         * Description for obridge.packages.converterObjects. Default: converters.
         */
        String converterObjects;
        /**
         * Description for obridge.packages.procedureContextObjects. Default: context.
         */
        String procedureContextObjects;
        /**
         * Description for obridge.packages.packageObjects. Default: packages.
         */
        String packageObjects;

    }

    @Value
    public static class Logging {
        String initializer;
        String method;
        Boolean enabled;
    }
}
