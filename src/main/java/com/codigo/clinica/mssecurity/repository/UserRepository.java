package com.codigo.clinica.mssecurity.repository;

import com.codigo.clinica.mssecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
