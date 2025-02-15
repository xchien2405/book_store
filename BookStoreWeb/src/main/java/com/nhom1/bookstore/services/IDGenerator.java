package com.nhom1.bookstore.services;

import java.security.SecureRandom;

public class IDGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";

    private static String generatorID() {
        SecureRandom random = new SecureRandom();
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int charIndex = random.nextInt(CHARACTERS.length());
            randomString.append(CHARACTERS.charAt(charIndex));
        }

        for (int i = 0; i < 3; i++) {
            int digitIndex = random.nextInt(DIGITS.length());
            randomString.append(DIGITS.charAt(digitIndex));
        }

        return randomString.toString();
    }

    public static String IDBook() {
        return "B" + generatorID();
    }

    public static String IDOrder() {
        return "O" + generatorID();
    }
    public static void main(String[] args) {
        System.out.println(IDOrder());
        System.out.println(IDOrder());
    }
}
