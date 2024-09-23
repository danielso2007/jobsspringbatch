package com.springbatch.dbpartitionerlocaljob.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericsUtils {

    private static GenericsUtils singleInstance = null;

    private GenericsUtils() {
    }

    public static GenericsUtils getInstance() {
        if (singleInstance == null) {
            singleInstance = new GenericsUtils();
        }
        return singleInstance;
    }

    public GenericsInfo getGenericsInfo(Object instance) {
        Class<?>[] types = findGenericTypes(instance);
        return new GenericsInfo(types);
    }

    private Class<?>[] findGenericTypes(Object instance) {
        Type type = instance.getClass().getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            type = instance.getClass().getSuperclass().getGenericSuperclass();
        }

        ParameterizedType genericType = (ParameterizedType) type;
        int numParams = genericType.getActualTypeArguments().length;
        Class<?>[] result = new Class[numParams];

        for (int i = 0; i < numParams; ++i) {
            result[i] = (Class<?>) genericType.getActualTypeArguments()[i];
        }

        return result;
    }

}
