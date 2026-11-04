package com.example.onlieshopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlieshopping.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
}
