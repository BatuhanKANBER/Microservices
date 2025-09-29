package com.product.product.config;

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

    public static final String ORDER_CREATED_QUEUE = "order.created.queue";
    public static final String ORDER_CREATED_ROUTING_KEY = "order.created";

    public static final String ORDER_STATUS_QUEUE = "order.status.queue";
    public static final String ORDER_STATUS_ROUTING_KEY = "order.status";

    public static final String ORDER_AMOUNT_QUEUE = "order.amount.queue";
    public static final String ORDER_AMOUNT_ROUTING_KEY = "order.amount";

    public static final String PRODUCT_STOCK_QUEUE = "product.stock.queue";
    public static final String PRODUCT_STOCK_ROUTING_KEY = "product.stock";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Queue createdQueue() {
        return new Queue(ORDER_CREATED_QUEUE, true);
    }

    @Bean
    public Binding createdBinding(Queue createdQueue, DirectExchange exchange) {
        return BindingBuilder.bind(createdQueue).to(exchange).with(ORDER_CREATED_ROUTING_KEY);
    }

    @Bean
    public Queue statusQueue() {
        return new Queue(ORDER_STATUS_QUEUE, true);
    }

    @Bean
    public Binding statusBinding(Queue statusQueue, DirectExchange exchange) {
        return BindingBuilder.bind(statusQueue).to(exchange).with(ORDER_STATUS_ROUTING_KEY);
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
    public Queue productStockQueue() {
        return new Queue(PRODUCT_STOCK_QUEUE, true);
    }

    @Bean
    public Binding productStockBinding(Queue productStockQueue, DirectExchange exchange) {
        return BindingBuilder.bind(productStockQueue).to(exchange).with(PRODUCT_STOCK_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
