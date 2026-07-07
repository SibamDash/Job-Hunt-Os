package com.jobhuntos.validator;
public class RequiredFieldValidator {
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    public static boolean isNotNull(Object value) {
        return value != null;
    }
}
