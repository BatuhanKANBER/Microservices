package com.basket.basket.models;

import java.io.Serializable;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@RedisHash("baskets")
@Data
@EqualsAndHashCode
public class Basket implements Serializable {

    @Id
    @UuidGenerator
    private String id;
    private Long productId;
    private Long userId;

    // public Basket() {
    // this.id = UUID.randomUUID().toString();
    // }

    // public Basket(Long productId, Long userId) {
    // this.id = UUID.randomUUID().toString();
    // this.productId = productId;
    // this.userId = userId;
    // }
}