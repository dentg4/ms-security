package com.codigo.clinica.mssecurity.service;

import com.codigo.clinica.mssecurity.entities.User;
import com.codigo.clinica.mssecurity.request.SignInRequest;
import com.codigo.clinica.mssecurity.request.SignUpRequest;
import com.codigo.clinica.mssecurity.request.TokenRequest;
import com.codigo.clinica.mssecurity.response.AuthenticationResponse;
import com.codigo.clinica.mssecurity.response.TokenResponse;

public interface AuthenticationService {
    User signUpUser(SignUpRequest signUpRequest);
    User signUpAdmin(SignUpRequest signUpRequest);
    AuthenticationResponse signIn(SignInRequest signInRequest);
    TokenResponse validateToken(TokenRequest tokenRequest);
}