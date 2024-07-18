package com.hamza.student_management_system.core.security;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Value("${jwt.refreshExpiration}")
    private long refreshExpirationTime;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, expirationTime);
    }

    public String generateRefreshToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, refreshExpirationTime);
    }

    private String createToken(Map<String, Object> claims, String username, long expiration) {
        claims.put("username", username);
        Instant now = Instant.now();

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuer("Hamza")
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expiration)))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractUserNameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("username", String.class);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims (String token){
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken (String token, UserDetails userDetails){
        final String username = extractUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
