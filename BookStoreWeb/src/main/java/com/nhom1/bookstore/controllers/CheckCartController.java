package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckCartController {
    @GetMapping("/giohang")
    public String checkCart() {
        return "giohang";
    }
}
