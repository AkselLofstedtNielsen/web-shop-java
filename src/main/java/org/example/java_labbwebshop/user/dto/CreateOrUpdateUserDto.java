package org.example.java_labbwebshop.user.dto;

import lombok.Data;

@Data
public class CreateOrUpdateUserDto {
    private String email;
    private String password;
    private String role;
}
