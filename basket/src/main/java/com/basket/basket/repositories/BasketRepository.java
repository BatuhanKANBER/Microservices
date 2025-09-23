package com.basket.basket.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.basket.basket.models.Basket;

@Repository
public interface BasketRepository extends CrudRepository<Basket, String> {

}
