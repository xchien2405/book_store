package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.services.BookService;

@Controller
public class ViewBookDetailsController {
    private final BookService bookService;

    public ViewBookDetailsController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/sach/{id}")
    public String getBookDetails(@PathVariable("id") String id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "chitiet";
    }
}
