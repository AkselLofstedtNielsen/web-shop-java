package org.example.java_labbwebshop.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.java_labbwebshop.apidiscog.model.DiscogsReleaseResponse;

@Data
@AllArgsConstructor
public class CartItem {
    private DiscogsReleaseResponse release;
    private int quantity;
}
