package com.nhom1.bookstore.entity;

public class Book {
    private String bookID;
    private String bookName;
    private String bookImage;
    private String bookAuthor;
    private String bookPublisher;
    private int bookStock;
    private int bookSold;
    private String bookPrice;
    private double bookWeight;
    private String bookSize;
    private String bookIntroduction;

    public Book() {}
    public Book(String bookID, String bookName, String bookImage, String bookAuthor, String bookPublisher, int bookStock, String bookPrice,
            int bookSold, double bookWeight, String bookSize, String bookIntroduction) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.bookAuthor = bookAuthor;   
        this.bookPublisher = bookPublisher;
        this.bookStock = bookStock;
        this.bookPrice = bookPrice;
        this.bookSold = bookSold;
        this.bookWeight = bookWeight;
        this.bookSize = bookSize;
        this.bookIntroduction = bookIntroduction;
    }

    public String getBookID() {
        return bookID;
    }
    public void setBookID(String bookID) {
        this.bookID = bookID;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getBookImage() {
        return bookImage;
    }
    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }
    public String getBookAuthor() {
        return bookAuthor;
    }
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
    public String getBookPublisher() {
        return bookPublisher;
    }
    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }
    public int getBookStock() {
        return bookStock;
    }
    public void setBookStock(int bookStock) {
        this.bookStock = bookStock;
    }
    public String getBookPrice() {
        return bookPrice;
    }
    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }
    public int getBookSold() {
        return bookSold;
    }
    public void setBookSold(int bookSold) {
        this.bookSold = bookSold;
    }
    public double getBookWeight() {
        return bookWeight;
    }
    public void setBookWeight(double bookWeight) {
        this.bookWeight = bookWeight;
    }
    public String getBookSize() {
        return bookSize;
    }
    public void setBookSize(String bookSize) {
        this.bookSize = bookSize;
    }
    public String getBookIntroduction() {
        return bookIntroduction;
    }
    public void setBookIntroduction(String bookIntroduction) {
        this.bookIntroduction = bookIntroduction;
    }
}
