package com.order.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.order.model.Order;
import com.order.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController implements IController<Order> {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }
}
