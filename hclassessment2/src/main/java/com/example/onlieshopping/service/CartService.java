package com.example.onlieshopping.service;

import java.time.LocalDate;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.onlieshopping.entity.Cart;
import com.example.onlieshopping.entity.CartItem;
import com.example.onlieshopping.entity.Customer;
import com.example.onlieshopping.entity.Product;
import com.example.onlieshopping.repository.CartRepository;
import com.example.onlieshopping.repository.CartItemRepository;
import com.example.onlieshopping.repository.CustomerRepository;
import com.example.onlieshopping.repository.ProductRepository;
import com.example.onlieshopping.dto.CartItemRequest;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
                       CustomerRepository customerRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    private Cart getOrCreateCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        return cartRepository.findByCustomerCustomerId(customerId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setCustomer(customer);
            cart.setCreatedDate(LocalDate.now());
            cart.setItems(new ArrayList<>());
            return cartRepository.save(cart);
        });
    }

    @Transactional
    public Cart addToCart(Long customerId, CartItemRequest request) {
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        Cart cart = getOrCreateCart(customerId);
        Product product = productRepository.findById(request.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        CartItem item = cartItemRepository
            .findByCartCartIdAndProductProductId(cart.getCartId(), product.getProductId())
            .orElse(null);
        int newQuantity = request.getQuantity() + (item == null ? 0 : item.getQuantity());
        if (newQuantity > product.getQuantity()) {
            throw new IllegalArgumentException("Insufficient product stock");
        }
        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setPrice(product.getPrice());
            item.setQuantity(newQuantity);
            cart.getItems().add(item);
        } else {
            item.setQuantity(newQuantity);
            item.setPrice(product.getPrice());
        }
        cartItemRepository.save(item);
        return cart;
    }

    @Transactional(readOnly = true)
    public Cart getCart(Long customerId) {
        return cartRepository.findByCustomerCustomerId(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
    }

    @Transactional
    public Cart updateCartItem(Long cartItemId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        CartItem item = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        if (quantity > item.getProduct().getQuantity()) {
            throw new IllegalArgumentException("Insufficient product stock");
        }
        item.setQuantity(quantity);
        item.setPrice(item.getProduct().getPrice());
        return item.getCart();
    }

    @Transactional
    public void removeCartItem(Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        Cart cart = item.getCart();
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
    }

    @Transactional
    public void clearCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerCustomerId(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
    }
}
