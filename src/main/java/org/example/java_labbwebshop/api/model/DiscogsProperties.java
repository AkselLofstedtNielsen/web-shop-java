package org.example.java_labbwebshop.api.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "discogs")
public class DiscogsProperties {

    private String baseUrl;
    private String token;
    private Map<String, String> endpoints;

    public String getSearchEndpoint() {
        return endpoints.get("search");
    }

    public String getReleaseEndpoint() {
        return endpoints.get("release");
    }

}

