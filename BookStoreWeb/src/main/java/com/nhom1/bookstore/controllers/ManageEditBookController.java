package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.services.BookService;

@Controller
public class ManageEditBookController {
    private final BookService bookService;

    public ManageEditBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/quantri/sanpham/chinhsua/{id}")
    public String viewEditBook(@PathVariable("id") String id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);
        return "admin_editproduct";
    }
    
    @PostMapping("/quantri/sanpham/chinhsua/{id}")
    public String editBook(@PathVariable("id") String id,
        @RequestParam("image") MultipartFile file,
        @RequestParam("path") String filePath,
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
        newBook.setBookID(id);
        newBook.setBookName(name);
        newBook.setBookPrice(price);
        newBook.setBookAuthor(author);
        newBook.setBookPublisher(publisher);
       
        double weight = 0;
        if(!weightRaw.isBlank()) {weight = Double.parseDouble(weightRaw);}
        newBook.setBookWeight(weight);

        newBook.setBookSize(size);

        int stock = 0;
        if (stockRaw != null) {stock = Integer.parseInt(stockRaw);}
        newBook.setBookStock(stock);

        newBook.setBookIntroduction(introduction);

        String path = filePath;
        String fileName = file.getOriginalFilename();

        if(fileName != "") {
            path = bookService.fileToFilePathConverter(file);
        }
        newBook.setBookImage(path);

        bookService.editBook(newBook);
        return "redirect:/quantri/sanpham/"+ newBook.getBookID();
    }
}
