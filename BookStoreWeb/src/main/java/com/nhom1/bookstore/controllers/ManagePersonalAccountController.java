package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.services.AccountService;

import jakarta.servlet.http.HttpSession;


@Controller
public class ManagePersonalAccountController {
    private final AccountService accountService;
    
    public ManagePersonalAccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @GetMapping("/taikhoan/thongtin")
    public String getPersonalAccount(HttpSession session, Model model) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if(loggedInUser != null) {
            Account account = accountService.getAccountNonPassword(loggedInUser.toString());
            model.addAttribute("account", account);
            return "user_taikhoan";
        }
        return "redirect:/dangnhap";
    }

    @GetMapping("/taikhoan")
    public String directAccount(HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if(loggedInUser != null) {
            
            Object isAdmin = session.getAttribute("isAdmin");
            if(isAdmin != null && isAdmin.equals(Boolean.TRUE)) {
                return "redirect:/quantri/donhang";
            }
            return "redirect:/taikhoan/donhang";
        }
        return "redirect:/dangnhap";
    }
}
