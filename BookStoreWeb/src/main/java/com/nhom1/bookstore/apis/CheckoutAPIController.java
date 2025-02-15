package com.nhom1.bookstore.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.bookstore.DTO.OrderDTO;
import com.nhom1.bookstore.services.IDGenerator;
import com.nhom1.bookstore.services.OrderService;

@RestController
public class CheckoutAPIController {
    private final OrderService orderService;

    public CheckoutAPIController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("api/accounts/{username}/orders")
    public ResponseEntity<String> createOrder(@PathVariable("username") String userID, @RequestBody OrderDTO orderDTO) {
        String orderID = IDGenerator.IDOrder();
        orderService.createOrder(userID, orderID, orderDTO);
        return new ResponseEntity<>(orderID, HttpStatus.CREATED);
    }
}
