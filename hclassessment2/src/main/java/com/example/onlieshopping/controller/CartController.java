package com.example.onlieshopping.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.example.onlieshopping.entity.Cart;
import com.example.onlieshopping.dto.CartItemRequest;
import com.example.onlieshopping.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add/{customerId}/{productId}")
    public Cart addToCart(@PathVariable Long customerId,
                          @PathVariable Long productId,
                          @RequestParam(defaultValue = "1") Integer quantity) {
        CartItemRequest request = new CartItemRequest();
        request.setProductId(productId);
        request.setQuantity(quantity);
        return cartService.addToCart(customerId, request);
    }

    @GetMapping("/{customerId}")
    public Cart getCart(@PathVariable Long customerId) {
        return cartService.getCart(customerId);
    }

    @PutMapping("/update/{cartItemId}")
    public Cart updateCartItem(@PathVariable Long cartItemId,
                               @RequestParam Integer quantity) {
        return cartService.updateCartItem(cartItemId, quantity);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public String removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return "Product removed from cart successfully";
    }

    @DeleteMapping("/clear/{customerId}")
    public String clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return "Cart cleared successfully";
    }
}
