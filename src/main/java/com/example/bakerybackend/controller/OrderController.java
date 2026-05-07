package com.example.bakerybackend.controller;

import com.example.bakerybackend.entity.Order;
import com.example.bakerybackend.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {
    private final OrderRepository orderRepository;
    public OrderController(OrderRepository orderRepository) { this.orderRepository = orderRepository; }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        orderRepository.save(order);
        return ResponseEntity.ok("{\"message\": \"Заказ на напитки принят!\"}");
    }
}