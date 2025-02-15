package com.nhom1.bookstore.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.services.AccountService;

import jakarta.servlet.http.HttpSession;


@Controller
public class ManageAccountController {
    private final AccountService accountService;

    public ManageAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/quantri/taikhoan")
    public String manageAccount(Model model, HttpSession session) {
        Object isAdmin = session.getAttribute("isAdmin");
        if(isAdmin != null && isAdmin.equals(Boolean.TRUE)) {
            List<Account> accountList = accountService.getAccountList();
            List<Account> userList = new ArrayList<>();
            for (Account account : accountList) {
                if(!account.isAdmin()) {
                    userList.add(account);
                }
            }
            model.addAttribute("accountList", userList);
            return "admin_account";
        }
        return "redirect:/dangnhap";
    }
}
