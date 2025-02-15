package com.nhom1.bookstore.repositories;

import java.util.List;
import org.springframework.stereotype.Controller;

import com.nhom1.bookstore.entity.Order;
import com.nhom1.bookstore.entity.OrderDetail;

@Controller
public class OrderDAOControllerImpl implements OrderDAOController{
    public final OrderDAO orderDAO;

    public OrderDAOControllerImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void editStatusOrder(String currentID, int newStatus) {
        orderDAO.editStatusOrder(currentID, newStatus);
    }

    @Override
    public Order getOrder(String id) {
        return orderDAO.getOrder(id);
    }

    @Override
    public List<Order> getOrderList() {
        return orderDAO.getOrderList();
    }

    @Override
    public List<Order> search(String tuKhoa) {
        return orderDAO.search(tuKhoa);
    }

    @Override
    public OrderDetail getOrderDetail(String id) {
        return orderDAO.getOrderDetail(id);
    }

    @Override
    public void createOrder(Order newOrder) {
        orderDAO.createOrder(newOrder);
    }

    @Override
    public void createOrderDetail(OrderDetail newOrderDetail) {
        orderDAO.createOrderDetail(newOrderDetail);
    }

    @Override
    public void deleteOrder(String id) {
        orderDAO.deleteOrder(id);
    }

    @Override
    public List<Order> getPOrderList(String userid) {
       return  orderDAO.getPOrderList(userid);
    }

    
}
