package com.nhom1.bookstore.services;

import java.text.NumberFormat;
import java.util.Locale;

public class ConverterCurrency {
    public static String numberToCurrency(int number){
        Locale vietnameseLocale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(vietnameseLocale);
        return currencyFormat.format(number);
    }

    public static int currencyToNumber(String currency){
        String giaRaw = currency.replaceAll("\\D", "");
        return Integer.parseInt(giaRaw);
    }
}
