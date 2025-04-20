package org.example.java_labbwebshop.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.java_labbwebshop.product.Product;

@Data
@AllArgsConstructor
public class CartItem {
    private Product product;
    private int quantity;
}

