package br.com.accounting.core.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.apache.commons.lang3.StringUtils.isBlank;

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

    public static boolean isBlankOrNull(final String value) {
        return isBlank(value) || value.equals("null");
    }

    public static String removeLast(final StringBuilder builder, final String value) {
        int index = builder.lastIndexOf(value);
        return builder.substring(0, index);
    }

    public static Double getDoubleFromString(final String value) throws ParseException {
        try {
            return Double.parseDouble(value.replaceAll(",", "."));
        }
        catch (NumberFormatException e) {
            return decimalFormat.parse(value).doubleValue();
        }
    }

    public static LocalDate getDateFromString(final String date) {
        return LocalDate.parse(date, DATE_FORMATTER);
    }

    public static Boolean getBooleanFromString(final String value) {
//        if (isBlank(value)) {
//            return false;
//        }
        return value.equals("S");
    }

    public static String getStringFromDouble(final Double value) {
        return decimalFormat.format(value);
    }

    public static String getStringFromDate(final LocalDate localDate) {
        return localDate.format(DATE_FORMATTER);
    }

    public static String getStringFromDateNextMonth(String date) {
        LocalDate localDate = getDateFromString(date);
        localDate = localDate.plusMonths(1L);
        return getStringFromDate(localDate);
    }

    public static String getStringFromCurrentDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    public static String getStringFromCurrentDateNextMonth() {
        return getStringFromDateNextMonth(getStringFromCurrentDate());
    }

    public static String getStringFromBoolean(final Boolean value) {
        return (value != null) && value ? "S" : "N";
    }

//    public static boolean entreDatas(LocalDate data, LocalDate dataInicial, LocalDate dataFinal) {
//        long intervaloDataInicial = DAYS.between(dataInicial, data);
//        long intervaloDataFinal = DAYS.between(data, dataFinal);
//
//        return (intervaloDataInicial >= 0) && (intervaloDataFinal >= 0);
//    }


}
