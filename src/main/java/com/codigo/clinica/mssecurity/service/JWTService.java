package com.codigo.clinica.mssecurity.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {

    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    boolean validateToken(String token, UserDetails userDetails);
    Claims extractAllClaims(String token);
    boolean isTokenExpired(String token);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}