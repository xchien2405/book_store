package com.nhom1.bookstore.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/data_book";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (JDBC.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }
}
