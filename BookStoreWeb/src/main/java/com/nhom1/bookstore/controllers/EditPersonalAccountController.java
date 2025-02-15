package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.services.AccountService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EditPersonalAccountController {
    private final AccountService accountService;
    
    public EditPersonalAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/taikhoan/thongtin")
    public String changeInformation(HttpSession session,
    @RequestParam("hoten") String hoten, 
    @RequestParam("sdt") String sdt, 
    @RequestParam("diachi") String diachi) {
        Account account = new Account();
        account.setUserName(hoten);
        account.setUserPhone(sdt);
        account.setUserAddress(diachi);

        Object loggedInUser = session.getAttribute("loggedInUser");
        accountService.editAccount(loggedInUser.toString(), account);
        return "redirect:/taikhoan/thongtin";
    }
}
