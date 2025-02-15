package com.nhom1.bookstore.entity;

public class Account {
    private String userID;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userAddress;
    private boolean isAdmin;
    private String userPassword;

    public Account() {}
    public Account(String userID, String userName, String userPhone, String userEmail, String userAddress, boolean isAdmin) {
        this.userID = userID;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.isAdmin = isAdmin;
    }
    
    public Account(String id, String userName, String userPhone, String userEmail, String userAddress, boolean isAdmin, String userPassword) {
        this.userID = id;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
        this.isAdmin = isAdmin;
        this.userPassword = userPassword;
    }
    public Account(String userID, String userEmail, String userPassword) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String Id) {
        this.userID = Id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String Email) {
        this.userEmail = Email;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String DiaChi) {
        this.userAddress = DiaChi;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String MatKhau) {
        this.userPassword = MatKhau;
    }
}

