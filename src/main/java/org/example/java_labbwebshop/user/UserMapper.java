package org.example.java_labbwebshop.user;

import org.example.java_labbwebshop.user.dto.UserDto;
import org.example.java_labbwebshop.user.dto.CreateOrUpdateUserDto;

public class UserMapper {

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        return dto;
    }

    public static User fromDto(CreateOrUpdateUserDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        if (dto.getRole() != null && !dto.getRole().isBlank()) {
            user.setRole(User.Role.valueOf(dto.getRole().toUpperCase()));
        } else {
            user.setRole(User.Role.USER); // fallback till USER
        }

        return user;
    }

}
