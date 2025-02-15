package com.nhom1.bookstore.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.entity.Order;
import com.nhom1.bookstore.services.BookService;
import com.nhom1.bookstore.services.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ManagePersonalOrdersController {
    private final OrderService orderService;
    private final BookService bookService;

    public ManagePersonalOrdersController(OrderService orderService, BookService bookService) {
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @GetMapping("/taikhoan/donhang")
    public String viewPersonalOrders(HttpSession session, Model model) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if(loggedInUser != null) {
            List<Order> orderList = orderService.search(loggedInUser.toString());
            for (Order order : orderList) {
                Book book = bookService.getBook(order.getOrderFirstBookID());
                order.setOrderFirstBook(book);
                order.setOrderItemQuantity(order.getOrderItemQuantity()-1);
            }
            
            model.addAttribute("orderList", orderList);
            return "user_donhang";
        }
        return "redirect:/dangnhap";
    }
}
