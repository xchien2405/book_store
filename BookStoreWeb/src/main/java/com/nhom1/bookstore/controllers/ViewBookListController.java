package com.nhom1.bookstore.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.services.BookService;

@Controller
public class ViewBookListController {
    private final BookService bookService;

    public ViewBookListController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/danhsach")
    public String getBookList(Model model) {
        List<Book> bookList = bookService.getBookList();
        model.addAttribute("bookList", bookList);
        return "danhsach";
    }
}