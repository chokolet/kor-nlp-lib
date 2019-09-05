package com.github.chokolet.util;

import java.util.Arrays;

public class ReflectionUtils {

    private ReflectionUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Object getEnumMatchedKey(String enumType, Class<? extends Enum> getType) throws InvalidFormatException {
        try {
            return Enum.valueOf(getType, enumType.toUpperCase());
        } catch (Exception err) {
            throw new InvalidFormatException(enumType, getType);
        }
    }

    public static String createEnumConstantStr(Class<?>... targetType) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Class<?> elem : targetType) {
            if (elem.isEnum()) {
                stringBuilder.append(Arrays.asList(elem.getEnumConstants()));
            }
        }
        return stringBuilder.toString();

    }

}
