package com.alura.foroHub.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

    public UserDetails findByCorreoElectronico(String subject);

    public boolean existsByCorreoElectronico(String correo);

    public boolean existsByCorreoElectronicoAndIdNot(String correo, Long id);
}
