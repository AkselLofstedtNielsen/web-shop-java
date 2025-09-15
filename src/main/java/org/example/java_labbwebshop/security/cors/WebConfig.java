package org.example.java_labbwebshop.security.cors;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                // 🔓 API endpoints
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");

                // 🔓 Swagger endpoints
                registry.addMapping("/v3/api-docs/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET");

                registry.addMapping("/swagger-ui/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET");
            }
        };
    }
}
