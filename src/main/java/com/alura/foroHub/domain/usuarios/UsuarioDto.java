package com.alura.foroHub.domain.usuarios;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Usuario")
public record UsuarioDto(Long id, String nombre, String correoElectronico) {
    public UsuarioDto(Usuario autor) {
        this(autor.getId(), autor.getNombre(), autor.getCorreoElectronico());
    }
}
