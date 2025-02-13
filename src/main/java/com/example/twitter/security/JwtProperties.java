package com.example.twitter.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private String secretKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";
    private long accessTokenExpiration = 1000 * 60 * 60 * 3; // 3 saat
    private long refreshTokenExpiration = 1000 * 60 * 60 * 24 * 7; // 7 gün
} 