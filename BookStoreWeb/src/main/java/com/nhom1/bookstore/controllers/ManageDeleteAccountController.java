package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhom1.bookstore.services.AccountService;

@Controller
public class ManageDeleteAccountController {
    private final AccountService accountService;

    public ManageDeleteAccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @GetMapping("/quantri/taikhoan/xoa/{id}")
    public String deleteAccount(@PathVariable("id") String id) {
        accountService.deleteAccount(id);
        return "redirect:/quantri/taikhoan"; 
    }
}
