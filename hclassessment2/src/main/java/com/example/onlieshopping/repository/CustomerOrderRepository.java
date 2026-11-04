package com.example.onlieshopping.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.onlieshopping.entity.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    List<CustomerOrder> findByCustomerCustomerId(Long customerId);
}
