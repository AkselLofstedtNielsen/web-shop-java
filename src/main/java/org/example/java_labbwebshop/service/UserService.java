package org.example.java_labbwebshop.service;

import org.example.java_labbwebshop.User.SessionUser;
import org.example.java_labbwebshop.User.User;
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

    @Autowired
    private SessionUser sessionUser;

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(sessionUser::setUser); // Sparar användaren i sessionen vid inloggning
        return user;
    }

    public User getLoggedInUser() {
        return sessionUser.getUser(); // Returnerar den inloggade användaren från sessionen
    }
}
