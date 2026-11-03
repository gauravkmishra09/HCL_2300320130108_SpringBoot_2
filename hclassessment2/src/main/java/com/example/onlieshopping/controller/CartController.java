

package com.example.onlineshopping.controller;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.example.onlieshopping.entity.*;
import com.example.onlineshopping.dto.CartItemRequest;
import com.example.onlineshopping.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/{customerId}/items")
    public Cart addToCart(
            @PathVariable Long customerId,
            @Valid @RequestBody CartItemRequest request) {

        return cartService.addToCart(customerId, request);
    }

    // GET - View cart
    @GetMapping("/{customerId}")
    public Cart getCart(@PathVariable Long customerId) {

        return cartService.getCart(customerId);
    }

    // PUT - Update product quantity
    @PutMapping("/{customerId}/items/{productId}")
    public Cart updateCartItem(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        return cartService.updateCartItem(
                customerId, productId, quantity);
    }

    // DELETE - Remove particular product
    @DeleteMapping("/{customerId}/items/{productId}")
    public String removeCartItem(
            @PathVariable Long customerId,
            @PathVariable Long productId) {

        cartService.removeCartItem(customerId, productId);

        return "Product removed from cart successfully";
    }
    @DeleteMapping("/{customerId}")
    public String clearCart(@PathVariable Long customerId) {

        cartService.clearCart(customerId);

        return "Cart cleared successfully";
    }
}


