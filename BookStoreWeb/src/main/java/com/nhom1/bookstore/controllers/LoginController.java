package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nhom1.bookstore.services.AccountService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final AccountService accountService;

    public LoginController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/dangnhap")
    public String viewLogin(Model model) {
        return "login";
    }

    @PostMapping("/dangnhap")
    public String authentication(@RequestParam("login-username") String username,
    @RequestParam("login-password") String password, Model model, HttpSession session) {
        int ketqua = accountService.authentication(username, password);
        if (ketqua != 0) {
            if (ketqua >= 1) {
                session.setAttribute("loggedInUser", username);
                if(ketqua > 1) {
                    session.setAttribute("isAdmin", true);
                    return "redirect:/quantri";
                } else{
                    session.setAttribute("isAdmin", false);
                    return "redirect:/taikhoan/thongtin";
                }
            } else {
                model.addAttribute("thongbao", "Tài khoản hoặc mật khẩu không chính xác.");
            }
        } else {
            model.addAttribute("thongbao", "Tài khoản không tồn tại.");
        }
        return "login";
    }
}
