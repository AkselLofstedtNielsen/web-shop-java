package org.example.java_labbwebshop.service;

import org.example.java_labbwebshop.model.User;
import org.example.java_labbwebshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import java.util.List;
import java.util.Optional;

@Service
@ApplicationScope //Default onödig
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email);
    }
}
