package com.nhom1.bookstore.apis;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.entity.Order;
import com.nhom1.bookstore.services.BookService;
import com.nhom1.bookstore.services.OrderService;

@RestController
public class GetOrderListAPIController {
    private final OrderService orderService;
    private final BookService bookService;

    public GetOrderListAPIController(OrderService orderService, BookService bookService) {
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @GetMapping("/api/orders")
    public ResponseEntity<List<Order>> getOrderList() {
        List<Order> orderList = orderService.getOrderList();
        for (Order order : orderList) {
            Book book = bookService.getBook(order.getOrderFirstBookID());
            order.setOrderFirstBook(book);
            order.setOrderItemQuantity(order.getOrderItemQuantity() - 1);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }
}
