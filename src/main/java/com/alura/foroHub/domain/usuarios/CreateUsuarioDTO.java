package com.alura.foroHub.domain.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateUsuarioDTO(
        @NotBlank String nombre,
        @NotBlank @Email String correoElectronico,
        @NotBlank @Size(min = 6, max = 50) String contrasena
) {
}
