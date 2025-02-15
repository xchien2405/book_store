package com.nhom1.bookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.entity.Order;
import com.nhom1.bookstore.entity.OrderDetail;
import com.nhom1.bookstore.entity.OrderDetail.OrderItem;
import com.nhom1.bookstore.services.AccountService;
import com.nhom1.bookstore.services.BookService;
import com.nhom1.bookstore.services.ConverterCurrency;
import com.nhom1.bookstore.services.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewPersonalOrderDetailsController {
    private final OrderService orderService;
    private final BookService bookService;
    private final AccountService accountService;

    public ViewPersonalOrderDetailsController(OrderService orderService, BookService bookService, AccountService accountService) {
        this.orderService = orderService;
        this.bookService = bookService;
        this.accountService = accountService;
    }

    @GetMapping("/taikhoan/donhang/{id}")
    public String viewPersonalOrder(@PathVariable("id") String id, Model model, HttpSession session) {
        Object loggedInUser = session.getAttribute("loggedInUser");
        if(loggedInUser != null) {
            Order order = orderService.getOrder(id);
            OrderDetail orderDetail = orderService.getOrderDetail(id);
            Account account = accountService.getAccountNonPassword(order.getUserID());
            
            for (OrderItem bookInOrder : orderDetail.getOrderItemList()) {
                Book book = bookService.getBook(bookInOrder.getBookID());
                bookInOrder.setBook(book);
            }
            int tongTienInt = ConverterCurrency.currencyToNumber(order.getOrderPrice());
            String tongTien = ConverterCurrency.numberToCurrency(tongTienInt + 25000);
            model.addAttribute("account", account);
            model.addAttribute("order", order);
            model.addAttribute("bookList", orderDetail.getOrderItemList());
            model.addAttribute("orderDetail", orderDetail);
            model.addAttribute("totalValue", tongTien);
            return "user_chitietdonhang";
        }
        return "redirect:/trangchu";
    }
}
