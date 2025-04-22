package org.example.java_labbwebshop.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.java_labbwebshop.order.model.Order;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; //DEFAULT USER

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)

    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    public enum Role {
        USER, ADMIN
    }
}
