package org.example.java_labbwebshop.order.repositories;

import org.example.java_labbwebshop.order.model.Order;
import org.example.java_labbwebshop.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}