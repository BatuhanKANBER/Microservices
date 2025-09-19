package com.order.order.dtos;

import com.order.order.models.Order;
import com.order.order.models.Status;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEvent {
    private Long id;
    private Long productId;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private Status status;

    public OrderEvent(Order order) {
        setId(order.getId());
        setProductId(order.getProductId());
        setQuantity(order.getQuantity());
        setStatus(order.getStatus());
    }
}
