package com.codigo.clinica.mssecurity.repository;

import com.codigo.clinica.mssecurity.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findByRolName(String rol);
}
