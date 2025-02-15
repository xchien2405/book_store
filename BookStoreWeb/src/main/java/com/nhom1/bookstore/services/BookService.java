package com.nhom1.bookstore.services;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.nhom1.bookstore.entity.Book;

public interface BookService {
    List<Book> getBookList();
    Book getBook(String id);
    void addBook(Book newBook);
    void editBook(Book newBook);
    void deleteBook(String id);
    List<Book> search(String tuKhoa);
    List<Book> getTopSelling();
    String fileToFilePathConverter(MultipartFile file);
    void updateQuantity(String id, int daBan);
}
