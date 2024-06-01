package com.codigo.clinica.mssecurity.service.impl;


import java.util.*;

import com.codigo.clinica.mssecurity.entities.Rol;
import com.codigo.clinica.mssecurity.entities.Role;
import com.codigo.clinica.mssecurity.entities.User;
import com.codigo.clinica.mssecurity.repository.RolRepository;
import com.codigo.clinica.mssecurity.repository.UserRepository;
import com.codigo.clinica.mssecurity.request.SignInRequest;
import com.codigo.clinica.mssecurity.request.SignUpRequest;
import com.codigo.clinica.mssecurity.response.AuthenticationResponse;
import com.codigo.clinica.mssecurity.service.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private SignUpRequest signUpRequest;
    private SignInRequest signInRequest;
    private User user;
    private Rol roleUser;
    private Rol roleAdmin;

    @BeforeEach
    void setUp() {
        signUpRequest = new SignUpRequest();
        signUpRequest.setName("John");
        signUpRequest.setSurname("Doe");
        signUpRequest.setEmail("john.doe@example.com");
        signUpRequest.setPassword("password");

        signInRequest = new SignInRequest();
        signInRequest.setEmail("john.doe@example.com");
        signInRequest.setPassword("password");

        user = new User();
        user.setName("John");
        user.setSurname("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword(new BCryptPasswordEncoder().encode("password"));

        roleUser = new Rol();
        roleUser.setRolName(Role.USER.name());

        roleAdmin = new Rol();
        roleAdmin.setRolName(Role.ADMIN.name());

    }

    @Test
    void testSignUpUser_Success() {
        when(rolRepository.findByRolName(Role.USER.name())).thenReturn(Optional.of(roleUser));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = authenticationService.signUpUser(signUpRequest);

        verify(userRepository).save(any(User.class));
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void testSignUpUser_RoleNotFound() {
        when(rolRepository.findByRolName(Role.USER.name())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            authenticationService.signUpUser(signUpRequest);
        });
    }

    @Test
    void testSignUpAdmin_Success() {
        when(rolRepository.findByRolName(Role.ADMIN.name())).thenReturn(Optional.of(roleAdmin));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = authenticationService.signUpAdmin(signUpRequest);

        verify(userRepository).save(any(User.class));
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void testSignUpAdmin_RoleNotFound() {
        when(rolRepository.findByRolName(Role.ADMIN.name())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            authenticationService.signUpAdmin(signUpRequest);
        });
    }

    @Test
    void testSignIn_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any(User.class))).thenReturn("jwt-token");

        AuthenticationResponse response = authenticationService.signIn(signInRequest);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertEquals("jwt-token", response.getToken());
    }

    @Test
    void testSignIn_InvalidEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            authenticationService.signIn(signInRequest);
        });
    }
}
