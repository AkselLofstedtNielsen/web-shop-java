package org.example.java_labbwebshop;

import org.example.java_labbwebshop.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@Service
@ApplicationScope //Default onödig
public class UserService {
    @Autowired
    UserRepository userRepository;

    void save(User user) {
        userRepository.save(user);
    }


    List<User> findAll() {
        return userRepository.findAll();
    }
}
