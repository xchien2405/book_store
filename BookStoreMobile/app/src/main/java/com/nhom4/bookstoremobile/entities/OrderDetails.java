package com.nhom4.bookstoremobile.entities;

import java.util.List;

public class OrderDetails {
    private String orderID;
    private List<OrderItem> orderItemList;

    public OrderDetails() {
    }

    public OrderDetails(String orderID, List<OrderItem> orderItemList) {
        this.orderID = orderID;
        this.orderItemList = orderItemList;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> bookList) {
        this.orderItemList = bookList;
    }

    public class OrderItem {
        private String bookID;
        private Book book;
        private int quantity;

        public OrderItem(String bookID, int quantity) {
            this.bookID = bookID;
            this.quantity = quantity;
        }

        public String getBookID() {
            return bookID;
        }

        public void setBookID(String bookID) {
            this.bookID = bookID;
        }

        public Book getBook() {
            return book;
        }

        public void setBook(Book book) {
            this.book = book;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
