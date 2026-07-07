package com.jobhuntos.validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class DateValidator {
    public static boolean isAfterOrEqual(LocalDate start, LocalDate end) {
        if (start == null || end == null) return true;
        return !end.isBefore(start);
    }
    public static boolean isAfterOrEqual(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return true;
        return !end.isBefore(start);
    }
    public static boolean isFuture(LocalDate date) {
        if (date == null) return true;
        return date.isAfter(LocalDate.now());
    }
}
