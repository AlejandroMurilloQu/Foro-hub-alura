package com.alura.foroHub.domain.cursos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Schema Curso")
public record CursoDto(Long id, String nombre, Categoria categoria) {
    public CursoDto(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}
