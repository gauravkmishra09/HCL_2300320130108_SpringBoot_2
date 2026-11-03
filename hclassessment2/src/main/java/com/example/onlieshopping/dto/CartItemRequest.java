package com.example.onlieshopping.dto;

import jakarta.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {

    private Long productId;


    @Positive
    private Integer quantity;
}