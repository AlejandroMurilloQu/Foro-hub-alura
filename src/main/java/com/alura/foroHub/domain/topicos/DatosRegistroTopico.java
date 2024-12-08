package com.alura.foroHub.domain.topicos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
