package com.nhom1.bookstore.repositories;

import java.util.List;

import com.nhom1.bookstore.entity.Account;

public interface AccountDAO {
    List<Account> getAccountList();
    Account getAccount(String userName);
    void registerAccount(Account newAccount);
    void editAccount(String currentID, Account newAccount);
    void deleteAccount(String id);
    List<Account> search(String tuKhoa);
    void changePassword(String currentID, String newPassword);
}
