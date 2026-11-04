package com.example.onlieshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlieshopping.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
