package org.example.java_labbwebshop.user;

import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.user.dto.CreateOrUpdateUserDto;
import org.example.java_labbwebshop.user.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    public UserDto create(CreateOrUpdateUserDto createUserDto) {
        User user = userMapper.fromDto(createUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public Optional<UserDto> getById(Long id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    public List<UserDto> findByEmailContains(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email)
                .stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public ResponseEntity<?> findByRole(String role) {
        try {
            User.Role parsedRole = User.Role.valueOf(role.toUpperCase());
            List<UserDto> users = findByRole(parsedRole);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role. Valid roles are: USER, ADMIN");
        }
    }

    public List<UserDto> findByRole(User.Role role) {
        return userRepository.findByRole(role).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto update(Long id, CreateOrUpdateUserDto updateUserDto) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(updateUserDto.getEmail());
            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            // Default to USER if role is null/blank; otherwise parse safely
            String dtoRole = updateUserDto.getRole();
            User.Role role = (dtoRole != null && !dtoRole.isBlank())
                    ? User.Role.valueOf(dtoRole.toUpperCase())
                    : User.Role.USER;
            user.setRole(role);
            return userMapper.toDto(userRepository.save(user));
        }).orElse(null);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

}
