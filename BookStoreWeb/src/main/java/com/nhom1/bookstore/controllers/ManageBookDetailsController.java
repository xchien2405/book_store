package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.services.BookService;

@Controller
public class ManageBookDetailsController {
        private final BookService bookService;

    public ManageBookDetailsController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/quantri/sanpham/{id}")
    public String manageBookDetail(@PathVariable("id") String id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "admin_detailproduct";
    }
}
