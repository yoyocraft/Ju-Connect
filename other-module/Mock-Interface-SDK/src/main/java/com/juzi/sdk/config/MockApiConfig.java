package com.juzi.sdk.config;

import com.juzi.sdk.client.MockApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author codejuzi
 */
@Data
@Configuration
@ConfigurationProperties("juzi.api.client")
@ComponentScan
public class MockApiConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public MockApiClient mockApiClient() {
        return new MockApiClient(accessKey, secretKey);
    }
}
