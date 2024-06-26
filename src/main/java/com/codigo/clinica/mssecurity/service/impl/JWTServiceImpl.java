package com.codigo.clinica.mssecurity.service.impl;

import com.codigo.clinica.mssecurity.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${key.token}")
    private String keytoken;

    @Value("${key.token.expiration}")
    private Integer expirationInMinutes;

    @Override
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMinutes))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return null;
    }

    private Key getSignKey(){
        byte[] key = Decoders.BASE64.decode(keytoken);
        return Keys.hmacShaKeyFor(key);
    }

    @Override
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResult){
        final Claims claims = extractAllClaims(token);
        return claimsResult.apply(claims);
    }

    @Override
    public boolean isTokenExpired(String token){
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }
}
