package com.nhom1.bookstore.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.services.BookService;

@RestController
public class GetBookDetailsAPIController {
    private final BookService bookService;

    public GetBookDetailsAPIController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> manageBookDetail(@PathVariable("id") String id) {
        Book book = bookService.getBook(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
