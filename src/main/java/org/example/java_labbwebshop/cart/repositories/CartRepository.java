package org.example.java_labbwebshop.cart.repositories;

import org.example.java_labbwebshop.cart.model.Cart;
import org.example.java_labbwebshop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
