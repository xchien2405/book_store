package com.nhom1.bookstore.repositories;

import java.util.List;
import org.springframework.stereotype.Controller;

import com.nhom1.bookstore.entity.Account;

@Controller
public class AccountDAOControllerImpl implements AccountDAOController{
    public final AccountDAO accountDAO;

    public AccountDAOControllerImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public void deleteAccount(String id) {
        accountDAO.deleteAccount(id);
    }

    @Override
    public void editAccount(String currentID, Account newAccount) {
        accountDAO.editAccount(currentID, newAccount);
    }

    @Override
    public Account getAccount(String id) {
        return accountDAO.getAccount(id);
    }

    @Override
    public List<Account> getAccountList() {
        return accountDAO.getAccountList();
    }

    @Override
    public List<Account> search(String tuKhoa) {
        return accountDAO.search(tuKhoa);
    }

    @Override
    public void registerAccount(Account newAccount) {
        accountDAO.registerAccount(newAccount);
    }

    @Override
    public void changePassword(String currentID, String newPassword) {
        accountDAO.changePassword(currentID, newPassword);
    }
    
}
