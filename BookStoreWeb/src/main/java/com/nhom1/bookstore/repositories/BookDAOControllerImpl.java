package com.nhom1.bookstore.repositories;

import java.util.List;
import org.springframework.stereotype.Controller;

import com.nhom1.bookstore.entity.Book;

@Controller
public class BookDAOControllerImpl implements BookDAOController{
    public final BookDAO bookDAO;

    public BookDAOControllerImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public void addBook(Book newBook) {
        bookDAO.addBook(newBook);
    }

    @Override
    public void deleteBook(String id) {
        bookDAO.deleteBook(id);
    }

    @Override
    public void editBook(Book newBook) {
        bookDAO.editBook(newBook);
    }

    @Override
    public Book getBook(String id) {
        return bookDAO.getBook(id);
    }

    @Override
    public List<Book> getBookList() {
        return bookDAO.getBookList();
    }

    @Override
    public List<Book> search(String tuKhoa) {
        return bookDAO.search(tuKhoa);
    }

    @Override
    public void updateQuantity(String id, int daBan) {
        bookDAO.updateQuantity(id, daBan);
    }
}
