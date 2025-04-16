package org.example.java_labbwebshop.user;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import java.io.Serializable;

@Component
@SessionScope
@Data
public class SessionUser implements Serializable {
    private User user;
}

