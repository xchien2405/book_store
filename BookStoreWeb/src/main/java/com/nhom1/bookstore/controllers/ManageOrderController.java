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
public class ManageOrderController {
    private final OrderService orderService;
    private final BookService bookService;

    public ManageOrderController(OrderService orderService, BookService bookService) {
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @GetMapping("/quantri/donhang")
    public String manageOrder(Model model, HttpSession session) {
        Object isAdmin = session.getAttribute("isAdmin");
        if(isAdmin != null && isAdmin.equals(Boolean.TRUE)) {
            List<Order> orderList = orderService.getOrderList();
            for (Order order : orderList) {
                Book book = bookService.getBook(order.getOrderFirstBookID());
                order.setOrderFirstBook(book);
            }
            model.addAttribute("orderList", orderList);
            
            return "admin_order";
        }
        return "redirect:/dangnhap";
    }
}
