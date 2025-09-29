package com.payment.payment.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.payment.payment.config.RabbitConfig;
import com.payment.payment.dto.OrderCreatedEvent;
import com.payment.payment.dto.PaymentRequest;
import com.payment.payment.dto.PaymentResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final AmqpTemplate rabbitTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    @RabbitListener(queues = RabbitConfig.ORDER_AMOUNT_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println(event);
        if (event.getId() != null) {
            String key = "order:" + event.getId();
            String value = String.valueOf(event.getAmount());
            redisTemplate.opsForValue().set(key, value);
            System.out.println("Saved expectedAmount to Redis for order "
                    + event.getId() + " = " + event.getAmount());
        } else {
            System.out.println("Order ID is null, cannot save to Redis");
        }

    }

    public String makePayment(PaymentRequest paymentRequest) {
        String key = "order:" + paymentRequest.getOrderId();
        String value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return "CANCELLED";
        } else {
            double expectedAmount = Double.parseDouble(value.toString());
            if (paymentRequest.getAmount() == expectedAmount) {
                PaymentResponse event = new PaymentResponse(paymentRequest.getOrderId(), "APPROVED");
                rabbitTemplate.convertAndSend(
                        RabbitConfig.ORDER_EXCHANGE,
                        RabbitConfig.PAYMENT_STATUS_ROUTING_KEY,
                        event);
                System.out.println(event);
                return "APPROVED";
            } else {
                PaymentResponse event = new PaymentResponse(paymentRequest.getOrderId(), "CANCELLED");
                rabbitTemplate.convertAndSend(
                        RabbitConfig.ORDER_EXCHANGE,
                        RabbitConfig.PAYMENT_STATUS_ROUTING_KEY,
                        event);
                System.out.println(event);
                return "CANCELLED";
            }
        }

    }
}
