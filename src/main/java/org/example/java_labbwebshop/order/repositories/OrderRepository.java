package org.example.java_labbwebshop.order.repositories;

import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
