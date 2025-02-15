package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {
    
    @GetMapping("/dangxuat")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedInUser");
        session.removeAttribute("isAdmin");

        return "redirect:/trangchu";
    }
}
