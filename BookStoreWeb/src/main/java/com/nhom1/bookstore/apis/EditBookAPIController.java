package com.nhom1.bookstore.apis;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nhom1.bookstore.converter.ConverterJSON;
import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.services.BookService;

@RestController
public class EditBookAPIController {
    private final BookService bookService;

    public EditBookAPIController(BookService bookService) {
        this.bookService = bookService;
    }

    @PutMapping("/api/books/{id}")
    public void editBook(@PathVariable("id") String id,
            @RequestParam("image") MultipartFile file,
            @RequestParam("book") String bookJSON) {
        Book newBook = ConverterJSON.jsonToBookEntity(bookJSON);
        
        if(file.getOriginalFilename() != "") {
            String path = bookService.fileToFilePathConverter(file);
            newBook.setBookImage(path);
        }
        
        bookService.editBook(newBook);
    }
}
