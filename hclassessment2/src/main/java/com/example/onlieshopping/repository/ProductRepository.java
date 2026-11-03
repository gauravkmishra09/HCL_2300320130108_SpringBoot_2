package com.example.onlieshopping.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlineshopping.entity.Product;

public interface ProductRepository
        extends JpaRepository<Product, Long> {

}

