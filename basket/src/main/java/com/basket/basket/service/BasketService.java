package com.basket.basket.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.basket.basket.model.Basket;
import com.basket.basket.repository.BasketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;

    public Basket getBasket(String id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Basket couldnt find by id: " + id));
    }

    public List<Basket> getAllBaskets() {
        return (List<Basket>) basketRepository.findAll();
    }

    public Basket createBasket(Basket basket) {
        return basketRepository.save(basket);
    }

    public Basket updateBasket(String id, Basket basket) {
        Basket updatedBasket = getBasket(id);
        updatedBasket.setProductId(basket.getProductId());
        return basketRepository.save(updatedBasket);
    }

    public void deleteBasket(String id) {
        basketRepository.delete(getBasket(id));
    }
}
