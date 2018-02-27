package br.com.accounting.core.util;

import br.com.accounting.core.entity.Entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static List<? extends Entity> removeDuplicates(List<? extends Entity> list) {
        Set<? extends Entity> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }
}
