package com.basket.basket.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.basket.basket.model.Basket;

@Repository
public interface BasketRepository extends CrudRepository<Basket, String> {

}
