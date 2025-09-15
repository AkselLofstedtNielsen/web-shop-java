package org.example.java_labbwebshop.apidiscog.config;

import org.example.java_labbwebshop.apidiscog.model.DiscogsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(RestClient.Builder builder, DiscogsProperties discogsProperties) {
        return builder
                .baseUrl(discogsProperties.getBaseUrl())
                .defaultHeader("Authorization", "Discogs token=" + discogsProperties.getToken())
                .build();
    }
}
