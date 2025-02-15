package com.nhom1.bookstore.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.repositories.AccountDAOController;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountDAOController accountDAOController;

    public AccountServiceImpl(AccountDAOController accountDAOController) {
        this.accountDAOController = accountDAOController;
    }

    @Override
    public List<Account> getAccountList() {
        return accountDAOController.getAccountList();
    }

    @Override
    public Account getAccount(String userName) {
        return accountDAOController.getAccount(userName);
    }

    @Override
    public Account getAccountNonPassword(String userName) {
        Account account = getAccount(userName);
        account.setUserPassword("");
        return account;
    }

    @Override
    public void editAccount(String currentID, Account newAccount) {
        accountDAOController.editAccount(currentID, newAccount);
    }

    @Override
    public List<Account> search(String tuKhoa) {
        return accountDAOController.search(tuKhoa);
    }

    @Override
    public void deleteAccount(String id) {
        accountDAOController.deleteAccount(id);;
    }

    @Override
    public int authentication(String username, String password) {
        Account account = getAccount(username);
        if(account != null) {
            if(account.getUserPassword().equals(password)) {
                if(account.isAdmin()) {
                    return 99;
                }
                return 1;
            } else{
                return -1;
            }
        } else{
            return 0;
        }
    }

    @Override
    public boolean checkEmail(String email) {
        List<Account> accountList = search(email);
        if(accountList != null) {
            return false;
        } else{
            return true;
        }
    }

    @Override
    public boolean checkUsername(String id) {
        if(getAccount(id) != null) {
            return false;
        } else{
            return true;
        }
    }
    @Override
    public void registerAccount(Account newAccount){
        accountDAOController.registerAccount(newAccount);
    }
}
