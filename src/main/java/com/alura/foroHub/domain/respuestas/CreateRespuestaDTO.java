package com.alura.foroHub.domain.respuestas;

import jakarta.validation.constraints.NotBlank;

public record CreateRespuestaDTO(@NotBlank String mensaje) {
}
