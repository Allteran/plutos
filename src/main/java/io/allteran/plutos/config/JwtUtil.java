package io.allteran.plutos.config;

import io.allteran.plutos.domain.Role;
import io.allteran.plutos.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${jwt.expiration}")
    private String EXPIRATION_TIME;


    public String extractUsername(String authToken) {
        return getClaimsFromToken(authToken)
                .getSubject();

    }

    public Claims getClaimsFromToken(String authToken) {
        String key = Base64.getEncoder().encodeToString(SECRET.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    public String generateToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        final List<String> authorities = user.getRoles().stream()
                .map(Role::name)
                .collect(Collectors.toList());
        claims.put("roles", authorities);

        long expirationSeconds = Long.parseLong(EXPIRATION_TIME);
        Date creationDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + expirationSeconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .setIssuedAt(creationDate)
                .setExpiration(expirationDate)
                .compact();
    }

    public boolean validateToken(String authToken) {
        Claims claims = getClaimsFromToken(authToken);
        Date expDate = claims.getExpiration();
        boolean isTokenNotExpired = new Date().before(expDate);
        return isTokenNotExpired;
    }
}
