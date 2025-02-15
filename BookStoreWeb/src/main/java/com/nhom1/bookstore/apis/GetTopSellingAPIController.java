package com.nhom1.bookstore.apis;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.services.BookService;

@RestController
@RequestMapping("/api/topselling")
public class GetTopSellingAPIController {
    private final BookService bookService;

    public GetTopSellingAPIController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getTopSelling() {
        List<Book> bookList = bookService.getTopSelling();
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }
}
