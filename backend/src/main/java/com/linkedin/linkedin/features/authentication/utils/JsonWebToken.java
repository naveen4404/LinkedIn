package com.linkedin.linkedin.features.authentication.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JsonWebToken {

    @Value("${jwt_secret}")
    private String secret;

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email){
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(getKey())
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getEmailFromToken(String token){
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

}
