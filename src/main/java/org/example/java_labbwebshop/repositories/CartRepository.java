package org.example.java_labbwebshop.repositories;

import org.example.java_labbwebshop.user.User;
import org.example.java_labbwebshop.model.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
