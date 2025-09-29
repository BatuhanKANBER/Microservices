package com.product.product.service;

import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.product.product.config.RabbitConfig;
import com.product.product.dto.OrderEvent;
import com.product.product.model.Product;
import com.product.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final AmqpTemplate rabbitTemplate;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Couldnt find product with id: " + id));
    }

    public Product updateProduct(Product product, Long id) {
        Product updatedProduct = getProduct(id);
        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setStock(product.getStock());
        return productRepository.save(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @RabbitListener(queues = RabbitConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderEvent event) {

        Long orderId = (Long) event.getId();
        Long productId = (Long) event.getProductId();
        int quantity = (int) event.getQuantity();
        double amount = (double) event.getAmount();
        String status = (String) event.getStatus();

        if (isStockEnough(productId, quantity) == true) {
            OrderEvent newEvent = new OrderEvent(orderId, productId, quantity, amount, status);
            // Rabbit eventi gönderiliyor
            rabbitTemplate.convertAndSend(
                    RabbitConfig.ORDER_EXCHANGE,
                    RabbitConfig.ORDER_AMOUNT_ROUTING_KEY,
                    event);
            System.out.println(newEvent);
        } else {
            OrderEvent newEvent = new OrderEvent(orderId, productId, quantity, amount, status);
            newEvent.setStatus("CANCELLED");
            // Rabbit eventi gönderiliyor
            rabbitTemplate.convertAndSend(
                    RabbitConfig.ORDER_EXCHANGE,
                    RabbitConfig.ORDER_STATUS_ROUTING_KEY,
                    event);
            System.out.println(newEvent);
        }
    }

    @RabbitListener(queues = RabbitConfig.PRODUCT_STOCK_QUEUE)
    public void handleStock(OrderEvent event) {
        Long productId = (Long) event.getProductId();
        int quantity = (int) event.getQuantity();
        System.out.println("STOK" + event);
        updateStock(productId, quantity);
    }

    public boolean isStockEnough(Long productId, int quantity) {
        Product product = getProduct(productId);
        if (product.getStock() < quantity)
            return false;

        return true;
    }

    public void updateStock(Long productId, int quantity) {
        Product product = getProduct(productId);
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }
}
