package com.codigo.clinica.mssecurity.response;

import io.jsonwebtoken.Claims;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {
    private boolean isValid;
    private List<String> roles;
    private String username;
    private boolean isTokenExpired;
}
