package br.com.accounting.core.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.DAYS;

public final class Utils {
    public static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final String SEPARADOR = ";";

    private Utils() {
    }

    public static boolean isEmpty(String value) {
        return (value == null) || value.isEmpty() || value.equals("null");
    }

    public static LocalDate getDateFromString(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    public static boolean entreDatas(LocalDate data, LocalDate dataInicial, LocalDate dataFinal) {
        long intervaloDataInicial = DAYS.between(dataInicial, data);
        long intervaloDataFinal = DAYS.between(data, dataFinal);

        return (intervaloDataInicial >= 0) && (intervaloDataFinal >= 0);
    }
}
