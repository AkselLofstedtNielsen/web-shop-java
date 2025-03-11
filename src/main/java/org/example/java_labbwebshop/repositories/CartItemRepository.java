package org.example.java_labbwebshop.repositories;

import org.example.java_labbwebshop.model.cart.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
