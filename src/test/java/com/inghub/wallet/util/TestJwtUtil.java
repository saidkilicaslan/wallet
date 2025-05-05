package com.inghub.wallet.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class TestJwtUtil {

    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("my_super_secure_secret_key_123456!".getBytes());

    public static String generateToken(String subject, Map<String, Object> claims, long expirationMillis) {
        long nowMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + expirationMillis))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String generateCustomerToken(String tckn) {
        return generateToken(tckn, Map.of("role", "CUSTOMER"), 3600_000); // 1 saatlik token
    }

    public static String generateEmployeeToken(String username) {
        return generateToken(username, Map.of("role", "EMPLOYEE"), 3600_000);
    }
}