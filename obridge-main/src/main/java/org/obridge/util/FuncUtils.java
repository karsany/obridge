package org.obridge.util;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fkarsany on 2015.01.04..
 */
public final class FuncUtils {

    private FuncUtils() {
    }

    public static <T, F> List<F> pluck(String fieldName, Class<F> fieldType, List<T> list) {
        try {
            List<F> result = new ArrayList<F>();
            for (T element : list) {
                result.add(fieldType.cast(PropertyUtils.getProperty(element, fieldName)));
            }
            return result;
        } catch (Exception e) {
            throw new OBridgeException(e);
        }
    }

}
