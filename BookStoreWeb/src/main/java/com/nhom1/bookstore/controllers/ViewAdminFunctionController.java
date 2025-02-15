package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewAdminFunctionController {

    @GetMapping("/quantri")
    public String viewAdminFunction(HttpSession session) {
        Object isAdmin = session.getAttribute("isAdmin");
        if(isAdmin != null && isAdmin.equals(Boolean.TRUE)) {
            return "admin";
        }
        return "redirect:/trangchu"; 
    }
}
