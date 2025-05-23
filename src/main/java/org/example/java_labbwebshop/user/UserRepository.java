package org.example.java_labbwebshop.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByEmailContainingIgnoreCase(String email);
    List<User> findByRole(User.Role role);
}
