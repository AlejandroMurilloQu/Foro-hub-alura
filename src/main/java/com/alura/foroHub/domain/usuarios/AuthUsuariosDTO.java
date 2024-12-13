package com.alura.foroHub.domain.usuarios;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Schema Iniciar Sesion")
public record AuthUsuariosDTO(@NotBlank String email,@NotBlank String password) {
}
