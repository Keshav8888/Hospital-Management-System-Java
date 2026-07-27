package com.hospital.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hospital.enums.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Create Secret Key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generate JWT Token
    public String generateToken(String email, Role role) {

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith((SecretKey) getSigningKey())
                .compact();
    }

    // Extract all claims
    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract Email
    public String extractEmail(String token) {

        return extractAllClaims(token).getSubject();
    }

    // Extract Role
    public String extractRole(String token) {

        return extractAllClaims(token).get("role", String.class);
    }

    // Extract Expiration Date
    public Date extractExpiration(String token) {

        return extractAllClaims(token).getExpiration();
    }

    // Check Token Expired
    public boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    // Validate Token
    public boolean validateToken(String token, String email) {

        return extractEmail(token).equals(email)
                && !isTokenExpired(token);
    }

}