package com.alura.foroHub.domain.topicos;

import com.alura.foroHub.domain.cursos.CursoDto;
import com.alura.foroHub.domain.usuarios.UsuarioDto;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "Schema Datos Un Topico")
public record DatosDetalleTopicoGet(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, Status status, UsuarioDto autor, CursoDto curso) {

    public DatosDetalleTopicoGet(Topico topico){
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(), new UsuarioDto(topico.getAutor()), new CursoDto(topico.getCurso()));
    }
}
