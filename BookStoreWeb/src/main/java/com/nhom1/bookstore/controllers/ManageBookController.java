package com.nhom1.bookstore.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.services.BookService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ManageBookController {
    private final BookService bookService;

    public ManageBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/quantri/sanpham")
    public String manageBook(Model model, HttpSession session) {
        Object isAdmin = session.getAttribute("isAdmin");
        if(isAdmin != null && isAdmin.equals(Boolean.TRUE)) {
            List<Book> bookList = bookService.getBookList();
            model.addAttribute("bookList", bookList);
            return "admin_product";
        }
        return "redirect:/dangnhap";
    }
}
