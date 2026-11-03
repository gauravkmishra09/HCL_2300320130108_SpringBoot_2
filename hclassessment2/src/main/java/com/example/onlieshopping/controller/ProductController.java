package com.example.onlieshopping.controller;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.*;

import com.example.onlieshopping.entity.Product;
import com.example.onlieshopping.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // POST - Add Product
    @PostMapping
    public Product addProduct( @RequestBody Product product) {
        return productService.addProduct(product);
    }

    // GET - Get All Products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // GET - Get Product By ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // PUT - Update Product
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {

        return productService.updateProduct(id, product);
    }

    // DELETE - Delete Product
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product deleted successfully";
    }
}