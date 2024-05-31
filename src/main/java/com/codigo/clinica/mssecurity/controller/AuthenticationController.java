package com.codigo.clinica.mssecurity.controller;

import com.codigo.clinica.mssecurity.entities.User;
import com.codigo.clinica.mssecurity.request.SignInRequest;
import com.codigo.clinica.mssecurity.request.SignUpRequest;
import com.codigo.clinica.mssecurity.request.TokenRequest;
import com.codigo.clinica.mssecurity.response.AuthenticationResponse;
import com.codigo.clinica.mssecurity.response.TokenResponse;
import com.codigo.clinica.mssecurity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signupuser")
    public ResponseEntity<User> signUpUser(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUpUser(signUpRequest));
    }

    @PostMapping("/signupadmin")
    public ResponseEntity<User> signUpAdmin(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signUpAdmin(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }
    @PostMapping("/validatetoken")
    public ResponseEntity<TokenResponse> validateToken(@RequestBody TokenRequest tokenRequest){
        TokenResponse tokenResponse = authenticationService.validateToken(tokenRequest);
        if(tokenResponse == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        return ResponseEntity.ok(tokenResponse);
    }
}
