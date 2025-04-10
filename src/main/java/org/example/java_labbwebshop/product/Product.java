package org.example.java_labbwebshop.product;

import jakarta.persistence.*;
import lombok.Data;
import org.example.java_labbwebshop.category.Category;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Product() {}

    public Product(String name, double price, Category category) {
        this.name = name;
        this.price = BigDecimal.valueOf(price);
        this.category = category;
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
