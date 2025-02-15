package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.services.AccountService;

@Controller
public class ManageEditAccountController {
    private final AccountService accountService;
    
    public ManageEditAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/quantri/taikhoan/chinhsua/{id}")
    public String viewEditAccount(@PathVariable("id") String id, Model model) {
        Account account = accountService.getAccountNonPassword(id);
        model.addAttribute("account", account);
        return "admin_editaccount";
    }

    @PostMapping("/quantri/taikhoan/chinhsua/{id}")
    public String editAccount(@RequestParam("accountID") String id,
    @RequestParam("accountName") String hoten, 
    @RequestParam("accountPhone") String sdt, 
    @RequestParam("accountAdress") String diachi) {
        Account account = new Account();
        account.setUserName(hoten);
        account.setUserPhone(sdt);
        account.setUserAddress(diachi);

        accountService.editAccount(id, account);
        return "redirect:/quantri/taikhoan";
    }
}
