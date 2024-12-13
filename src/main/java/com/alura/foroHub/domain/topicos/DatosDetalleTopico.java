package com.alura.foroHub.domain.topicos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;


@Schema(name = "Schema Topico")
public record DatosDetalleTopico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, Long curso, Long autor) {
    public DatosDetalleTopico(Topico topicoSaved) {
        this(topicoSaved.getId(), topicoSaved.getTitulo(), topicoSaved.getMensaje(), topicoSaved.getFechaCreacion(), topicoSaved.getCurso().getId(), topicoSaved.getAutor().getId());
    }
}
