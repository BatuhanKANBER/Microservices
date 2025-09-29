package com.payment.payment.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String ORDER_EXCHANGE = "order.exchange";

    public static final String ORDER_AMOUNT_QUEUE = "order.amount.queue";
    public static final String ORDER_AMOUNT_ROUTING_KEY = "order.amount";

    public static final String PAYMENT_STATUS_QUEUE = "payment.status.queue";
    public static final String PAYMENT_STATUS_ROUTING_KEY = "payment.status";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Queue amountQueue() {
        return new Queue(ORDER_AMOUNT_QUEUE, true);
    }

    @Bean
    public Binding amountBinding(Queue amountQueue, DirectExchange exchange) {
        return BindingBuilder.bind(amountQueue).to(exchange).with(ORDER_AMOUNT_ROUTING_KEY);
    }

    @Bean
    public Queue statusQueue() {
        return new Queue(PAYMENT_STATUS_QUEUE, true);
    }

    @Bean
    public Binding statusBinding(Queue statusQueue, DirectExchange exchange) {
        return BindingBuilder.bind(statusQueue).to(exchange).with(PAYMENT_STATUS_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
