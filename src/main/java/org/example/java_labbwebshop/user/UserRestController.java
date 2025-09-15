package org.example.java_labbwebshop.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.example.java_labbwebshop.user.dto.CreateOrUpdateUserDto;
import org.example.java_labbwebshop.user.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth") // Alla endpoints här kräver JWT
public class UserRestController {

    // ADMIN
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    // ADMIN
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getByIdOrThrow(id));
    }

    // ADMIN
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> searchByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findByEmailContains(email));
    }

    // ADMIN
    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getByRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.findByRoleName(role));
    }

    // Inloggad användare
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        return ResponseEntity.ok(userService.findByEmailOrThrow(authentication.getName()));
    }

    // Inloggad användare
    @PutMapping("/me")
    public ResponseEntity<UserDto> updateCurrentUser(Authentication authentication,
                                                     @RequestBody CreateOrUpdateUserDto dto) {
        return ResponseEntity.ok(userService.updateByEmail(authentication.getName(), dto));
    }

    // ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private final UserService userService;
}
