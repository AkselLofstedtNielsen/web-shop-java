package org.example.java_labbwebshop.auth;

import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.auth.jwt.JwtUtil;
import org.example.java_labbwebshop.auth.login.LoginRequest;
import org.example.java_labbwebshop.auth.login.LoginResponse;
import org.example.java_labbwebshop.auth.login.RegisterRequest;
import org.example.java_labbwebshop.user.User;
import org.example.java_labbwebshop.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new LoginResponse(token);
    }

    public LoginResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AuthException("Email already in use");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.Role.USER)
                .build();

        userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
}

