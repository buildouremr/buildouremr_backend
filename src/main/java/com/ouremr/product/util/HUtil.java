package com.ouremr.product.util;

import java.util.regex.Pattern;

public class HUtil {

    public static boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static <T> T getOrDefault(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    public static boolean isValidEmail(String email) {
        if (!isValidString(email)) return false;

        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regex, email);
    }

    public static boolean isNullOrEmpty(Object obj) {
        return obj == null || (obj instanceof Object[] && ((Object[]) obj).length == 0);
    }
}
