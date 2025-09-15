package org.example.java_labbwebshop.auth.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
