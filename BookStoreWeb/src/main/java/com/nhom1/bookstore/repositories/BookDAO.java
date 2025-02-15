package com.nhom1.bookstore.repositories;

import java.util.List;

import com.nhom1.bookstore.entity.Book;

public interface BookDAO {
    List<Book> getBookList();
    Book getBook(String id);
    void addBook(Book newBook);
    void editBook(Book newBook);
    void deleteBook(String id);
    List<Book> search(String tuKhoa);
    void updateQuantity(String id, int daBan);
}
