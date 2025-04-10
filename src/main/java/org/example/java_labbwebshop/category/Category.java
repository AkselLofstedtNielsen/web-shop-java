package org.example.java_labbwebshop.category;

import jakarta.persistence.*;
import lombok.Data;
import org.example.java_labbwebshop.product.Product;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;
}
