package com.alura.foroHub.domain.usuarios;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Schema(name = "Crear usuario")
public record CreateUsuarioDTO(
        @NotBlank String nombre,
        @NotBlank @Email String correoElectronico,
        @NotBlank @Size(min = 6, max = 50) String contrasena
) {
}
