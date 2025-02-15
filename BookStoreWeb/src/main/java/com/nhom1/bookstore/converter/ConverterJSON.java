package com.nhom1.bookstore.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.entity.Book;

public class ConverterJSON {
    public static Book jsonToBookEntity(String bookJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Book book = objectMapper.readValue(bookJson, Book.class);
            return book;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Account jsonToAccountEntity(String accountJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Account account = objectMapper.readValue(accountJson, Account.class);
            return account;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }        
}
