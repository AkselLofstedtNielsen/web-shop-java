package org.example.java_labbwebshop.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
public class SessionUser {
    private User user;
}

