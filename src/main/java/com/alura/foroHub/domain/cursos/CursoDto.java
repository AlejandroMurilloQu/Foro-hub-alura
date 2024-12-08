package com.alura.foroHub.domain.cursos;

public record CursoDto(Long id, String nombre, Categoria categoria) {
    public CursoDto(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}
