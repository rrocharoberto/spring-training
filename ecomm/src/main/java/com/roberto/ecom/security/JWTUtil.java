package com.roberto.ecom.security;

import java.util.Date;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;

@Component
@Log
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration.time}")
    private Long expirationTime;

    public String generateToken(String userName) {
        return Jwts.builder()
            .setSubject(userName)
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(SignatureAlgorithm.HS512, secret.getBytes())
            .compact();
    }

    public boolean isTokenValid(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            String userName = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            if (userName != null && expirationDate != null && expirationDate.after(new Date())) {
                log.info("Token for user: " + userName + " is valid!");
                return true;
            }
        }
        return false;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            // if it could not get the claims from the token (because invalid or any other
            // problem)
            log.log(Level.WARNING, "Could not get Claims for token: " + token, e);
            return null;
        }
    }

    public String getUserName(String token) {
        Claims claims = getClaims(token);
        return claims == null ? null : claims.getSubject();
    }
}
