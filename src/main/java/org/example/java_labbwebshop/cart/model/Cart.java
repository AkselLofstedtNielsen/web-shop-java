package org.example.java_labbwebshop.cart.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.java_labbwebshop.user.User;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

}
