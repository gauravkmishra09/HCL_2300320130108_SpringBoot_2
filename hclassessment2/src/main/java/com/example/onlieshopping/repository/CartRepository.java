package com.example.onlieshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlieshopping.entity.*;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByCustomerCustomerId(Long customerId);
}