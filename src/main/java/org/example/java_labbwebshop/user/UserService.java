package org.example.java_labbwebshop.user;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.user.dto.CreateOrUpdateUserDto;
import org.example.java_labbwebshop.user.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto create(CreateOrUpdateUserDto dto) {
        User user = userMapper.fromDto(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto getByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserException("User with ID " + id + " not found"));
    }

    public List<UserDto> findByEmailContains(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserException("User with email " + email + " not found"));
    }

    public UserDto updateByEmail(String email, CreateOrUpdateUserDto dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User with email " + email + " not found"));

        user.setEmail(dto.getEmail());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserDto> findByRoleName(String roleName) {
        try {
            User.Role role = User.Role.valueOf(roleName.toUpperCase());
            return findByRole(role);
        } catch (IllegalArgumentException e) {
            throw new UserException("Invalid role: " + roleName);
        }
    }

    public List<UserDto> findByRole(User.Role role) {
        return userRepository.findByRole(role).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto update(Long id, CreateOrUpdateUserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User with ID " + id + " not found"));

        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        String dtoRole = dto.getRole();
        User.Role role = (dtoRole != null && !dtoRole.isBlank())
                ? User.Role.valueOf(dtoRole.toUpperCase())
                : User.Role.USER;

        user.setRole(role);
        return userMapper.toDto(userRepository.save(user));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
