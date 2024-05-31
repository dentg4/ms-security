package com.codigo.clinica.mssecurity.service.impl;

import com.codigo.clinica.mssecurity.entities.User;
import com.codigo.clinica.mssecurity.repository.UserRepository;
import com.codigo.clinica.mssecurity.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(()->
                        new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
