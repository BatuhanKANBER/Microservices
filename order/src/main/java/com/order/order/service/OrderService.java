package com.order.order.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.order.order.config.RabbitConfig;
import com.order.order.dto.OrderEvent;
import com.order.order.model.Order;
import com.order.order.model.Status;
import com.order.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AmqpTemplate rabbitTemplate;

    public Order createOrder(Order order) {
        order.setStatus(Status.CREATED);
        Order savedOrder = orderRepository.save(order);
        OrderEvent event = new OrderEvent(order);

        // Rabbit eventi gönderiliyor
        rabbitTemplate.convertAndSend(
                RabbitConfig.ORDER_EXCHANGE,
                RabbitConfig.ORDER_CREATED_ROUTING_KEY,
                event);

        System.out.println("Order oluşturuldu ve event gönderildi: " + event);

        return savedOrder;
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldnt find order with id: " + id));
    }

    @RabbitListener(queues = RabbitConfig.ORDER_STATUS_QUEUE)
    public void handleOrderCreated(OrderEvent event) {
        updateStatus(event.getId(), event.getStatus().toString());
    }

    public void updateStatus(Long orderId, String status) {
        Order order = getOrder(orderId);
        if (status == "ACCEPTED") {
            order.setStatus(Status.ACCEPTED);
        } else {
            order.setStatus(Status.REJECTED);
        }
        orderRepository.save(order);
    }

}
