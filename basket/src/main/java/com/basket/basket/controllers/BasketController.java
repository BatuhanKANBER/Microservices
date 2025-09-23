package com.basket.basket.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basket.basket.models.Basket;
import com.basket.basket.services.BasketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/baskets")
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;

    @GetMapping("/list")
    public ResponseEntity<List<Basket>> getAllBaskets() {
        return ResponseEntity.ok().body(basketService.getAllBaskets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Basket> getMethodName(@PathVariable String id) {
        return ResponseEntity.ok().body(basketService.getBasket(id));
    }

    @PostMapping("/create")
    public ResponseEntity<Basket> createBasket(@RequestBody Basket basket) {
        return ResponseEntity.ok().body(basketService.createBasket(basket));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Basket> updateBasket(@PathVariable String id, @RequestBody Basket basket) {
        return ResponseEntity.ok().body(basketService.updateBasket(id, basket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBasket(@PathVariable String id) {
        basketService.deleteBasket(id);
        return ResponseEntity.ok("Deleted basket with id: " + id);
    }
}
