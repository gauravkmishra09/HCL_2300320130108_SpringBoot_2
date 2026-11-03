package com.example.onlieshopping.service;



import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.onlieshopping.entity.*;
import com.example.onlieshopping.repository.*;
import com.example.onlieshopping.dto.CartItemRequest;

@Service
public class CartService<Cart> {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       CustomerRepository customerRepository,
                       ProductRepository productRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }
    private <Cart> Cart getOrCreateCart(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found"));

        return cartRepository.findByCustomerCustomerId(customerId)
                .orElseGet(() -> {

                    Cart cart = new Cart();

                    cart.setCustomer(customer);
                    cart.setCreatedDate(LocalDate.now());
                    cart.setItems(new ArrayList<>());

                    return cartRepository.save(cart);
                });
    }
    @Transactional
    public Cart addToCart(Long customerId, CartItemRequest request) {

        Cart cart = getOrCreateCart(customerId);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        if (request.getQuantity() > product.getQuantity()) {
            throw new RuntimeException("Insufficient product stock");
        }

        CartItem cartItem = cartItemRepository
                .findByCartCartIdAndProductProductId(
                        cart.getCartId(), product.getProductId())
                .orElse(null);

        if (cartItem != null) {

            int newQuantity = cartItem.getQuantity()
                    + request.getQuantity();

            if (newQuantity > product.getQuantity()) {
                throw new RuntimeException("Insufficient product stock");
            }

            cartItem.setQuantity(newQuantity);

        } else {

            cartItem = new CartItem();

            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice());

            cart.getItems().add(cartItem);
        }

        cartItemRepository.save(cartItem);

        return cart;
    }

    @Transactional(readOnly = true)
    public Cart getCart(Long customerId) {

        return cartRepository.findByCustomerCustomerId(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found"));
    }


    @Transactional
    public Cart updateCartItem(Long customerId,
                               Long productId,
                               Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        Cart cart = cartRepository
                .findByCustomerCustomerId(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        if (quantity > product.getQuantity()) {
            throw new RuntimeException("Insufficient product stock");
        }

        CartItem cartItem = cartItemRepository
                .findByCartCartIdAndProductProductId(
                        cart.getCartId(), productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found in cart"));

        cartItem.setQuantity(quantity);

        return cart;
    }

    @Transactional
    public void removeCartItem(Long customerId, Long productId) {

        Cart cart = cartRepository
                .findByCustomerCustomerId(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found"));

        CartItem cartItem = cartItemRepository
                .findByCartCartIdAndProductProductId(
                        cart.getCartId(), productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found in cart"));

        cart.getItems().remove(cartItem);
    }

    @Transactional
    public void clearCart(Long customerId) {

        Cart cart = cartRepository
                .findByCustomerCustomerId(customerId)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found"));

        cart.getItems().clear();
    }
}