package br.com.accounting.core.util;

import java.time.format.DateTimeFormatter;

public final class Utils {
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Utils() {
    }

    public static boolean isEmpty(String value) {
        return (value == null) || value.isEmpty() || value.equals("null");
    }
}
