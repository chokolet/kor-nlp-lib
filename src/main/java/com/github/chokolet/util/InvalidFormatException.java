package com.github.chokolet.util;

import java.util.Set;

public class InvalidFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException(String targetEnum, Class<?>... targetType) {
        super("No Constructors [" + targetEnum + "] check Constructors " + ReflectionUtils.createEnumConstantStr(targetType));
    }

    public InvalidFormatException(String targetEnum, Set<?> elemList) {
        super("No Constructors [" + targetEnum + "] check Constructors " + elemList);
    }


}
