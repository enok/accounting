package br.com.accounting.core.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static java.time.temporal.ChronoUnit.DAYS;

public final class Utils {
    public static final String SEPARADOR = ";";
    public static final Locale LOCALE = new Locale("pt", "BR");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final String DECIMAL_PATTERN = "###,###.00";

    private static DecimalFormat decimalFormat;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(LOCALE);
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        decimalFormat = new DecimalFormat(DECIMAL_PATTERN, symbols);
    }

    private Utils() {
    }

    public static boolean isBlank(String value) {
        return StringUtils.isBlank(value) || value.equals("null");
    }

//    public static LocalDate getDateFromString(String date) {
//        return LocalDate.parse(date, DATE_FORMATTER);
//    }
//
//    public static boolean entreDatas(LocalDate data, LocalDate dataInicial, LocalDate dataFinal) {
//        long intervaloDataInicial = DAYS.between(dataInicial, data);
//        long intervaloDataFinal = DAYS.between(data, dataFinal);
//
//        return (intervaloDataInicial >= 0) && (intervaloDataFinal >= 0);
//    }

//    public static String getStringFromDate(LocalDate localDate) {
//        return localDate.format(DATE_FORMATTER);
//    }

//    public static String getStringFromCurrentDate() {
//        return LocalDate.now().format(DATE_FORMATTER);
//    }
//
//    public static String getNextMonth(String date) {
//        LocalDate localDate = getDateFromString(date);
//        localDate = localDate.plusMonths(1L);
//        return getStringFromDate(localDate);
//    }
//
//    public static String getDoubleFormatted(Double value) {
//        return decimalFormat.format(value);
//    }

    public static Double createDouble(String value) throws ParseException {
        return Double.parseDouble(value.replaceAll(",", "."));
//        try {
//            return Double.parseDouble(value.replaceAll(",", "."));
//        }
//        catch (NumberFormatException e) {
//            return decimalFormat.parse(value).doubleValue();
//        }
    }
}
