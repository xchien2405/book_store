package com.nhom1.bookstore.services;

import java.util.List;

import com.nhom1.bookstore.DTO.OrderDTO;
import com.nhom1.bookstore.entity.Order;
import com.nhom1.bookstore.entity.OrderDetail;

public interface OrderService {
    List<Order> getOrderList();
    Order getOrder(String id);
    OrderDetail getOrderDetail(String id);
    void editStatusOrder(String currentID, int newStatus);
    List<Order> search(String tuKhoa);
    void createOrder(String idNguoiDat, String orderID, OrderDTO newOrder);
    void deleteOrder(String id);
    List<Order> getPOrderList(String id);
}
