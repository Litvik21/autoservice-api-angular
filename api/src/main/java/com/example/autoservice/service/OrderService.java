package com.example.autoservice.service;

import com.example.autoservice.model.Order;
import com.example.autoservice.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    Order save(Order order);

    Order update(Order order);

    Order addProduct(Long orderId, Product product);

    Order updateStatus(Long orderId, String status);

    BigDecimal getPrice(Long id);

    Order getById(Long id);

    List<Order> getAll();
}