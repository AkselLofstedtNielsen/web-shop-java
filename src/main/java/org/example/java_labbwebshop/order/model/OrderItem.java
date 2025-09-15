package org.example.java_labbwebshop.order.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Snapshot av Discogs-data
    @Column(nullable = false)
    private int releaseId;

    @Column(nullable = false)
    private String title;

    private String artist;
    private String coverImage;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;
}


