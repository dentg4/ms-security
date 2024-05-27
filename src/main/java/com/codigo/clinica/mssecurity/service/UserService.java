package com.codigo.clinica.mssecurity.service;

import com.codigo.clinica.mssecurity.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailService();
    List<User> getUsers();
}