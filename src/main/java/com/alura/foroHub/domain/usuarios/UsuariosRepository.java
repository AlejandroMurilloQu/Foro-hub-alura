package com.alura.foroHub.domain.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

    public UserDetails findByCorreoElectronico(String subject);
}
