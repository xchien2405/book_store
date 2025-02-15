package com.nhom4.bookstoremobile.converter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converter {
    public static Date stringtoDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.US);

        try {
            return inputFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        try {
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String numberToCurrency(int number) {
        Locale vietnameseLocale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(vietnameseLocale);
        return currencyFormat.format(number);
    }

    public static int currencyToNumber(String currency) {
        String giaRaw = currency.replaceAll("\\D", "");
        return Integer.parseInt(giaRaw);
    }
}
