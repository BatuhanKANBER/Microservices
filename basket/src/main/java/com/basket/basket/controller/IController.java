package com.basket.basket.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface IController<T, ID> {
    ResponseEntity<List<T>> getAll();

    ResponseEntity<T> get(ID id);

    ResponseEntity<T> create(T entity);

    ResponseEntity<T> update(T entity, ID id);

    ResponseEntity delete(ID id);
}
