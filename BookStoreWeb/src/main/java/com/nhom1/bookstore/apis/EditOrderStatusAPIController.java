package com.nhom1.bookstore.apis;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.bookstore.entity.Book;
import com.nhom1.bookstore.entity.OrderDetail;
import com.nhom1.bookstore.entity.OrderDetail.OrderItem;
import com.nhom1.bookstore.services.BookService;
import com.nhom1.bookstore.services.OrderService;

@RestController
public class EditOrderStatusAPIController {
    private final OrderService orderService;
    private final BookService bookService;

    public EditOrderStatusAPIController(OrderService orderService, BookService bookService) {
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @PutMapping("/api/orders/{orderID}")
    public void editOrderStatus(@PathVariable("orderID") String id, @RequestParam("orderStatus") int orderStatus) {
        if(orderStatus == 3) {
            OrderDetail orderDetail = orderService.getOrderDetail(id);

            for (OrderItem bookInOrder : orderDetail.getOrderItemList()) {
                Book book = bookService.getBook(bookInOrder.getBookID());
                bookService.updateQuantity(book.getBookID(), bookInOrder.getQuantity());
            }
        }
        orderService.editStatusOrder(id, orderStatus);
    }
}
