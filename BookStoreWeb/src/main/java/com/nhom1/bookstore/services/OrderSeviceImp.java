package com.nhom1.bookstore.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nhom1.bookstore.DTO.OrderDTO;
import com.nhom1.bookstore.entity.Order;
import com.nhom1.bookstore.entity.OrderDetail;
import com.nhom1.bookstore.repositories.OrderDAOController;

@Service
public class OrderSeviceImp implements OrderService {
    private final OrderDAOController orderDAOController;

    public OrderSeviceImp(OrderDAOController orderDAOController) {
        this.orderDAOController = orderDAOController;
    }

    @Override
    public List<Order> getOrderList() {
        List<Order> orderList = orderDAOController.getOrderList();
        sortByThoiGianDat(orderList);
        return orderList;
    }

    private void sortByThoiGianDat(List<Order> orderList) {
        Collections.sort(orderList, new Comparator<Order>() {
            @Override
            public int compare(Order order1, Order order2) {
                return order2.getOrderTime().compareTo(order1.getOrderTime());
            }
        });
    }

    @Override
    public Order getOrder(String id) {
        return orderDAOController.getOrder(id);
    }

    @Override
    public void editStatusOrder(String currentID, int newStatus) {
        orderDAOController.editStatusOrder(currentID, newStatus);
    }

    @Override
    public List<Order> search(String tuKhoa) {
        List<Order> orderList = orderDAOController.search(tuKhoa);
        sortByThoiGianDat(orderList);
        return orderList;
    }

    @Override
    public OrderDetail getOrderDetail(String id) {
        return orderDAOController.getOrderDetail(id);
    }

    @Override
    public void createOrder(String idNguoiDat, String orderID, OrderDTO newOrder) {
        
        Order order = new Order();
        order.setOrderID(orderID);
        order.setUserID(idNguoiDat);
        order.setOrderTime(new Date());
        int trangThai = 1;
        if(newOrder.getPaymentMethod().equals("cod")) {
            trangThai = 0;
        }
        order.setOrderStatus(trangThai);
        order.setOrderPrice(newOrder.getPrice());
        order.setOrderFirstBookID(newOrder.getBookList().get(0));
        order.setOrderItemQuantity(newOrder.getBookList().size());
        order.setOrderPhone(newOrder.getPhone());
        order.setOrderAddress(newOrder.getAddress());
        orderDAOController.createOrder(order);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderID(orderID);

        orderDetail.setOrderItemList(new ArrayList<>());
        
        for(int i = 0; i < newOrder.getBookList().size(); i++){
            String idSach = newOrder.getBookList().get(i);
            String soLuongRaw = newOrder.getQuantityList().get(i);
            int soLuong = Integer.parseInt(soLuongRaw);

            OrderDetail.OrderItem bookInOrder = orderDetail.new OrderItem(idSach, soLuong);
            orderDetail.getOrderItemList().add(bookInOrder);
        }

        orderDAOController.createOrderDetail(orderDetail);
    }

    @Override
    public void deleteOrder(String id) {
        orderDAOController.deleteOrder(id);
    }

    @Override
    public List<Order> getPOrderList(String userid) {
        List<Order> orderList = orderDAOController.getPOrderList(userid);
        sortByThoiGianDat(orderList);
        return orderList;
    }
}
