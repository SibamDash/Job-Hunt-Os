package com.jobhuntos.validator;
public class SalaryValidator {
    public static boolean isValid(String salary) {
        if (salary == null || salary.trim().isEmpty()) return true; // Optional field
        // Accepts formats like "100k", "100,000", "100000"
        return salary.matches("^[0-9,]+[kKmM]?$");
    }
}
