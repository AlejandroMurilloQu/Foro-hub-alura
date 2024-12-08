package com.alura.foroHub.domain.usuarios;

public record UsuarioDto(Long id, String nombre, String correoElectronico) {
    public UsuarioDto(Usuario autor) {
        this(autor.getId(), autor.getNombre(), autor.getCorreoElectronico());
    }
}
