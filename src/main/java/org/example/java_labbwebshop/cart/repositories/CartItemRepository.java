package org.example.java_labbwebshop.cart.repositories;

import org.example.java_labbwebshop.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
