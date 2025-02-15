package com.nhom1.bookstore.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.bookstore.entity.Account;
import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.entity.Order;
import com.nhom1.bookstore.entity.OrderDetail;
import com.nhom1.bookstore.entity.OrderDetail.OrderItem;
import com.nhom1.bookstore.services.AccountService;
import com.nhom1.bookstore.services.BookService;
import com.nhom1.bookstore.services.OrderService;

@RestController
public class GetOrderDetailsAPIController {
    private final OrderService orderService;
    private final BookService bookService;
    private final AccountService accountService;

    public GetOrderDetailsAPIController(OrderService orderService, BookService bookService, AccountService accountService) {
        this.orderService = orderService;
        this.bookService = bookService;
        this.accountService = accountService;
    }

    @GetMapping("/api/orders/{orderID}/details")
    public ResponseEntity<OrderDetail> getOrderDetail(@PathVariable("orderID") String id) {
        OrderDetail orderDetail = orderService.getOrderDetail(id);

        for (OrderItem bookInOrder : orderDetail.getOrderItemList()) {
            Book book = bookService.getBook(bookInOrder.getBookID());
            bookInOrder.setBook(book);
        }
        
        return new ResponseEntity<>(orderDetail, HttpStatus.OK);
    }

    @GetMapping("/api/orders/{orderID}")
    public ResponseEntity<Order> getOrder(@PathVariable("orderID") String id) {
        Order order = orderService.getOrder(id);
        if(order != null) {
            Book book = bookService.getBook(order.getOrderFirstBookID());
            order.setOrderFirstBook(book);
            order.setOrderItemQuantity(order.getOrderItemQuantity() - 1);
                
            Account account = accountService.getAccount(order.getUserID());
            order.setUserID(account.getUserName());
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
