package com.nhom4.bookstoremobile.entities;

import java.util.Date;

public class Order {
    private String orderID;
    private String userID;
    private Date orderTime;
    private String orderPhone;
    private String orderAddress;
    private String orderStatus;
    private String orderPrice;
    private String orderFirstBookID;
    private Book orderFirstBook;
    private int orderItemQuantity;

    public Order() {
    }

    public Order(String orderID, String userID, Date orderTime, int orderStatus, String orderPrice, String orderFirstBookID, int orderItemQuantity, String orderPhone, String orderAddress) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderTime = orderTime;
        this.orderStatus = convertStatusInt(orderStatus);
        this.orderPrice = orderPrice;
        this.orderFirstBookID = orderFirstBookID;
        this.orderItemQuantity = orderItemQuantity;
        this.orderPhone = orderPhone;
        this.orderAddress = orderAddress;
    }

    public Order(String orderID, String userID, int orderStatus, String orderPrice) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderStatus = convertStatusInt(orderStatus);
        this.orderPrice = orderPrice;
    }

    private String convertStatusInt(int orderStatus) {
        switch (orderStatus) {
            case 0:
                return "Chưa xác nhận";
            case 1:
                return "Đã xác nhận";
            case 2:
                return "Đang vận chuyển";
            case 3:
                return "Đã hoàn thành";
            default:
                return "Đã hủy";
        }
    }

    public int convertStatusString(String orderStatus) {
        switch (orderStatus) {
            case "Chưa xác nhận":
                return 0;
            case "Đã xác nhận":
                return 1;
            case "Đang vận chuyển":
                return 2;
            case "Đã hoàn thành":
                return 3;
            default:
                return 10;
        }
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String maDonHang) {
        this.orderID = maDonHang;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String idNguoiDat) {
        this.userID = idNguoiDat;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = convertStatusInt(orderStatus);
    }

    public int getOrderStatusInt() {
        return convertStatusString(orderStatus);
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderFirstBookID() {
        return orderFirstBookID;
    }

    public void setOrderFirstBookID(String idSachDau) {
        this.orderFirstBookID = idSachDau;
    }

    public Book getOrderFirstBook() {
        return orderFirstBook;
    }

    public void setOrderFirstBook(Book orderFirstBookID) {
        this.orderFirstBook = orderFirstBookID;
    }

    public int getOrderItemQuantity() {
        return orderItemQuantity;
    }

    public void setOrderItemQuantity(int orderItemQuantity) {
        this.orderItemQuantity = orderItemQuantity;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }
}