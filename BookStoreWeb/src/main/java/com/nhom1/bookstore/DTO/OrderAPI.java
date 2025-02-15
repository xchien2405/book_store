package com.nhom1.bookstore.DTO;

import java.util.List;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.entity.Order;

public class OrderAPI {
    private Order order;
    private List<Book> orderItem;
    private List<Integer> orderItemQuantity;

    public OrderAPI(Order order, List<Book> orderItem, List<Integer> orderItemQuantity) {
        this.order = order;
        this.orderItem = orderItem;
        this.orderItemQuantity = orderItemQuantity;
    }

    public OrderAPI() {}

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Book> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<Book> orderItem) {
        this.orderItem = orderItem;
    }

    public List<Integer> getOrderItemQuantity() {
        return orderItemQuantity;
    }

    public void setOrderItemQuantity(List<Integer> orderItemQuantity) {
        this.orderItemQuantity = orderItemQuantity;
    }
}
