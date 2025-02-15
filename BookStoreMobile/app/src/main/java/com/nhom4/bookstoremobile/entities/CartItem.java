package com.nhom4.bookstoremobile.entities;

public class CartItem {
    private String bookID;
    private int quantity;
    private Book book;

    public CartItem() {
    }

    public CartItem(String bookID, int quantity) {
        this.bookID = bookID;
        this.quantity = quantity;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
