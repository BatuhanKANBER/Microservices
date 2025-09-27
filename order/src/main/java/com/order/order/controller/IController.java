package com.order.order.controller;

import org.springframework.http.ResponseEntity;

public interface IController<T> {
    ResponseEntity<T> create(T entity);
}
