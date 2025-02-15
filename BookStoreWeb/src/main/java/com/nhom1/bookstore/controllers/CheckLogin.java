package com.nhom1.bookstore.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class CheckLogin {

    @ModelAttribute
    public void checkLogin(Model model, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if(loggedInUser != null) {
            model.addAttribute("userOptionsSection", true);
            Object isAdmin = session.getAttribute("isAdmin");
            if(isAdmin != null && isAdmin.equals(Boolean.TRUE)) {
                model.addAttribute("adminSection", false);
                model.addAttribute("accountSection", true);
            } else{
                model.addAttribute("adminSection", true);
                model.addAttribute("accountSection", false);
                model.addAttribute("accountID", loggedInUser.toString()); 
            }
        } else{
            model.addAttribute("userOptionsSection", false);
            model.addAttribute("adminSection", true);
            model.addAttribute("accountSection", true);
        }
    }
}
