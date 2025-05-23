package org.example.java_labbwebshop.user;

import lombok.AllArgsConstructor;
import org.example.java_labbwebshop.user.dto.CreateOrUpdateUserDto;
import org.example.java_labbwebshop.user.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private UserService userService;

    @PostMapping
    public UserDto createUser(@RequestBody CreateOrUpdateUserDto createUserDto) {
        return userService.create(createUserDto);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<UserDto> getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/search")
    public List<UserDto> searchByEmail(@RequestParam String email) {
        return userService.findByEmailContains(email);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<?> getByRole(@PathVariable String role) {
        try {
            User.Role parsedRole = User.Role.valueOf(role.toUpperCase());
            List<UserDto> users = userService.findByRole(parsedRole);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid role. Valid roles are: USER, ADMIN");
        }
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody CreateOrUpdateUserDto dto) {
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

}