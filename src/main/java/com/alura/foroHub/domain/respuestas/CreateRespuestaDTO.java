package com.alura.foroHub.domain.respuestas;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Schema Respuesta")
public record CreateRespuestaDTO(@NotBlank String mensaje) {
}
