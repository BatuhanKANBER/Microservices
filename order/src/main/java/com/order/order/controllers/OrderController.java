package com.order.order.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.order.models.Order;
import com.order.order.services.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    // @PatchMapping("/update/{id}")
    // public ResponseEntity<Order> updateStatus(@PathVariable Long id) {
    // return ResponseEntity.ok(orderService.updateStatus(id));
    // }
}
