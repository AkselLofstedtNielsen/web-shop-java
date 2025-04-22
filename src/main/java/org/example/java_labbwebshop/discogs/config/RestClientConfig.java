package org.example.java_labbwebshop.discogs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .baseUrl("https://api.discogs.com")
                .defaultHeader("Authorization", "Discogs token=TCdgqdBNaMimdlfMJJikcKWvrfDlqFbjtGCosbaj")
                .build();
    }
}
