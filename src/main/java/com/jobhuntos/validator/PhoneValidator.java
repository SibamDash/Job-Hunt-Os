package com.jobhuntos.validator;
import java.util.regex.Pattern;
public class PhoneValidator {
    // Basic phone validation for international or local numbers
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?[0-9]{10,15}$");
    public static boolean isValid(String phone) {
        if (phone == null || phone.trim().isEmpty()) return false;
        String digitsOnly = phone.replaceAll("[\\s\\-\\(\\)]", "");
        return PHONE_PATTERN.matcher(digitsOnly).matches();
    }
}
