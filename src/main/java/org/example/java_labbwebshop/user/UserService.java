package org.example.java_labbwebshop.user;

import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.user.dto.CreateOrUpdateUserDto;
import org.example.java_labbwebshop.user.dto.UserDto;
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

    public UserDto create(CreateOrUpdateUserDto createUserDto) {
        User user = UserMapper.fromDto(createUserDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public Optional<UserDto> getById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDto);
    }

    public List<UserDto> findByEmailContains(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email)
                .stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public List<UserDto> findByRole(User.Role role) {
        return userRepository.findByRole(role).stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public UserDto update(Long id, CreateOrUpdateUserDto updateUserDto) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(updateUserDto.getEmail());
            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            user.setRole(User.Role.valueOf(updateUserDto.getRole().toUpperCase()));
            return UserMapper.toDto(userRepository.save(user));
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
