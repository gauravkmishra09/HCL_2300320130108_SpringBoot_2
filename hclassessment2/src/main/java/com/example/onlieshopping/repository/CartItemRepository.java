package com.example.onlieshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlieshopping.*;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartCartIdAndProductProductId(
            Long cartId, Long productId);
}