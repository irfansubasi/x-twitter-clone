package com.example.twitter.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtProperties jwtProperties;

    //kullanıcı adını tokenden çıkar
    public String extractUsername(String token) {
        String username = extractClaim(token, Claims::getSubject);
        log.debug("tokenden çıkarılmış username: {}", username);
        return username;
    }

    //jwt içinde saklanan varsa claimi yani veriyi çıkarır
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //kullanıcı için access token üretir
    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails);
    }

    //user kimliğini doğrulamak için kısa süreli access token üret
    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String token = buildToken(extraClaims, userDetails, jwtProperties.getAccessTokenExpiration());
        log.debug("access tokeni oluşturuldu: {}", token);
        return token;
    }

    //access token süresi dolunca refresh token üretir
    public String generateRefreshToken(UserDetails userDetails) {
        String token = buildToken(new HashMap<>(), userDetails, jwtProperties.getRefreshTokenExpiration());
        log.debug("refresh tokeni oluşturuldu: {}", token);
        return token;
    }

    //jwt oluşturur -- subject olarak user eklenir -- expiration eklenir -- imzalanır
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //token geçerli mi değil mi
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        boolean isValid = (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
        return isValid;
    }

    //token süresi dolmuş mu
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //tüm claimleri yani verileri tokenden çıkarır
    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("Error JWT token", e);
            throw e;
        }
    }

    //jwt imzalama ve doğrulama için secret keyi alır
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
} 