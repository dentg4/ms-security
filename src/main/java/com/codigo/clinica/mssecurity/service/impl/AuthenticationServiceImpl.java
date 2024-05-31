package com.codigo.clinica.mssecurity.service.impl;

import com.codigo.clinica.mssecurity.entities.Rol;
import com.codigo.clinica.mssecurity.entities.Role;
import com.codigo.clinica.mssecurity.entities.User;
import com.codigo.clinica.mssecurity.repository.RolRepository;
import com.codigo.clinica.mssecurity.repository.UserRepository;
import com.codigo.clinica.mssecurity.request.SignInRequest;
import com.codigo.clinica.mssecurity.request.SignUpRequest;
import com.codigo.clinica.mssecurity.response.AuthenticationResponse;
import com.codigo.clinica.mssecurity.service.AuthenticationService;
import com.codigo.clinica.mssecurity.service.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Transactional
    @Override
    public User signUpUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setEmail(signUpRequest.getEmail());
        Set<Rol> assignedRoles = new HashSet<>();
        Rol userRole = rolRepository.findByRolName(Role.USER.name()).orElseThrow(() -> new RuntimeException("Error: Rol not found."));
        assignedRoles.add(userRole);
        user.setRoles(assignedRoles);
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User signUpAdmin(SignUpRequest signUpRequest) {
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setEmail(signUpRequest.getEmail());
        Set<Rol> assignedRoles = new HashSet<>();
        Rol userRole = rolRepository.findByRolName(Role.ADMIN.name()).orElseThrow(() -> new RuntimeException("Error: Rol not found."));
        assignedRoles.add(userRole);
        user.setRoles(assignedRoles);
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        return userRepository.save(user);

    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            signInRequest.getEmail(),signInRequest.getPassword()));
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("Email no valido"));

        var jwt = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }

}
