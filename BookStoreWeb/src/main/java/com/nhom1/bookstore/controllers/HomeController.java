package com.nhom1.bookstore.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.services.BookService;

@Controller
public class HomeController {
    private final BookService bookService;

    public HomeController(BookService BookService) {
        this.bookService = BookService;
    }

    @GetMapping("/")
    public String getHome(Model model) {
        List<Book> bookList = bookService.getTopSelling();
        model.addAttribute("bookList", bookList);
        return "trangchu";
    }

    @GetMapping("/trangchu")
    public String getHomeMain(Model model) {
        List<Book> bookList = bookService.getTopSelling();
        model.addAttribute("bookList", bookList);
        return "trangchu";
    }
}
