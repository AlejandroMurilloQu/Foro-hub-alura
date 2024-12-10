package com.alura.foroHub.domain.usuarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthUsuariosDTO(@NotBlank String email,@NotBlank String password) {
}
