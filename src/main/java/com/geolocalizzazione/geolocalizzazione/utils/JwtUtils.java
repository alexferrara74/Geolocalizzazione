package com.geolocalizzazione.geolocalizzazione.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtils {

    private final String secret = "dl0KyAKyTeAYte7YU7R0OvLbsSovZ1af"; // cambia in produzione
    private final long expiration = 1000 * 60 * 60; // 1 ora

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }
}
