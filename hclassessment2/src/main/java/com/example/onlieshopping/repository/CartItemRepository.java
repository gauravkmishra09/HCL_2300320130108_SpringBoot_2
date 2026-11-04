package com.example.onlieshopping.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlieshopping.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartCartIdAndProductProductId(Long cartId, Long productId);
}
