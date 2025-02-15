package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.services.AccountService;

@Controller
public class RegisterAccountController {
    private final AccountService accountService;

    public RegisterAccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/dangky")
    public String viewRegister(Model model) {
        return "register";
    }

    @PostMapping("/dangky")
    public String register(Model model,
    @RequestParam("register-email") String email, 
    @RequestParam("register-username") String username, 
    @RequestParam("register-password") String password1, 
    @RequestParam("confirm-password") String password2) {
        if(checkEmail(email)) {
            if(accountService.checkUsername(username)){
                if(password1.equals(password2)) {
                    accountService.registerAccount(new Account(username, email, password1));
                    return "redirect:/dangnhap";
                } else{
                    model.addAttribute("thongbao", "Mật khẩu nhập lại không chính xác");
                    return "redirect:/dangky";
                }
            } else{
                model.addAttribute("thongbao", "Tên đăng nhập này đã được sử dụng");
                return "redirect:/dangky";
            }
        } else{
            model.addAttribute("thongbao", "Email này đã được sử dụng");
            return "redirect:/dangky";
        }
    }

    boolean checkEmail(String email) {
        if(accountService.checkEmail(email)) {
            return false;
        } else{
            return true;
        }
    }
}
