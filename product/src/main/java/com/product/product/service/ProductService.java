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

        updateStock(orderId, productId, quantity);
    }

    public void updateStock(Long orderId, Long productId, int quantity) {
        Product product = getProduct(productId);

        if (product.getStock() < quantity) {
            OrderEvent event = new OrderEvent(orderId, productId, quantity, "REJECTED");
            // Rabbit eventi gönderiliyor
            rabbitTemplate.convertAndSend(
                    RabbitConfig.ORDER_EXCHANGE,
                    RabbitConfig.ORDER_STATUS_ROUTING_KEY,
                    event);
            System.out.println(event);
        } else {
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            OrderEvent event = new OrderEvent(orderId, productId, quantity, "ACCEPTED");
            // Rabbit eventi gönderiliyor
            rabbitTemplate.convertAndSend(
                    RabbitConfig.ORDER_EXCHANGE,
                    RabbitConfig.ORDER_STATUS_ROUTING_KEY,
                    event);
            System.out.println(event);
        }
    }
}
