package com.alura.foroHub.domain.topicos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(name = "Schema registrar topico")
public record DatosRegistroTopico(
        @NotEmpty
        String titulo,
        @NotEmpty
        String mensaje,
        @NotNull
        Long autor,
        @NotNull
        Long curso
) {
}
