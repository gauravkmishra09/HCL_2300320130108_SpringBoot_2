package com.example.onlieshopping.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.onlieshopping.entity.Customer;
import com.example.onlieshopping.repository.CustomerRepository;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + id));
    }

    @Transactional
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existing = getCustomerById(id);
        if (!existing.getEmail().equals(updatedCustomer.getEmail())
                && customerRepository.existsByEmail(updatedCustomer.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        existing.setCustomerName(updatedCustomer.getCustomerName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setPassword(updatedCustomer.getPassword());
        existing.setMobile(updatedCustomer.getMobile());
        existing.setCity(updatedCustomer.getCity());
        return existing;
    }

    public void deleteCustomer(Long id) {
        customerRepository.delete(getCustomerById(id));
    }
}
