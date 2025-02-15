package com.nhom1.bookstore.services;

import java.util.List;

import com.nhom1.bookstore.entity.Account;


public interface AccountService {
    List<Account> getAccountList();
    Account getAccount(String userName);
    Account getAccountNonPassword(String userName);
    void editAccount(String currentID, Account newAccount);
    void deleteAccount(String id);
    List<Account> search(String tuKhoa);
    int authentication(String username, String password);
    boolean checkEmail (String email);
    boolean checkUsername (String id);
    void registerAccount(Account newAccount);
}
