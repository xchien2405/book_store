package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.services.BookService;
import com.nhom1.bookstore.services.IDGenerator;


@Controller
public class ManageAddBookController {
    private final BookService bookService;

    public ManageAddBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/quantri/sanpham/them")
    public String viewAddBook() {
        return "admin_addproduct";
    }
    
    @PostMapping("/quantri/sanpham/them")
    public String addBook(
        @RequestParam("image") MultipartFile file,
        @RequestParam("name") String name,
        @RequestParam("price") String price,
        @RequestParam("author") String author,
        @RequestParam("publisher") String publisher,
        @RequestParam("weight") String weightRaw,
        @RequestParam("size") String size,
        @RequestParam("stock") String stockRaw,
        @RequestParam("introduction") String introduction
    ) {
        Book newBook = new Book();
        newBook.setBookID(IDGenerator.IDBook());
        newBook.setBookName(name);
        newBook.setBookPrice(price);
        newBook.setBookAuthor(author);
        newBook.setBookPublisher(publisher);
       
        double weight = 0;
        if(!weightRaw.isBlank()) {weight = Double.parseDouble(weightRaw);}
        newBook.setBookWeight(weight);

        newBook.setBookSize(size);

        int stock = Integer.parseInt(stockRaw);
        newBook.setBookStock(stock);

        newBook.setBookIntroduction(introduction);

        String filePath = bookService.fileToFilePathConverter(file);
        newBook.setBookImage(filePath);
        
        bookService.addBook(newBook);
        return "redirect:/quantri/sanpham/"+ newBook.getBookID();
    }
}