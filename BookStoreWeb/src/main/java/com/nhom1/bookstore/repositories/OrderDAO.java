package com.nhom1.bookstore.repositories;

import java.util.List;

import com.nhom1.bookstore.entity.Order;
import com.nhom1.bookstore.entity.OrderDetail;

public interface OrderDAO {
    List<Order> getOrderList();
    Order getOrder(String id);
    OrderDetail getOrderDetail(String id);
    void editStatusOrder(String currentID, int newStatus);
    List<Order> search(String tuKhoa);
    void createOrder(Order newOrder);
    void createOrderDetail(OrderDetail newOrderDetail);
    void deleteOrder(String id);
    List<Order> getPOrderList(String id);
}
