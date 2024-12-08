package com.alura.foroHub.domain.topicos;

import com.alura.foroHub.domain.cursos.Curso;
import com.alura.foroHub.domain.usuarios.Usuario;

import java.time.LocalDateTime;

public record DatosDetalleTopico(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, Long curso, Long autor) {
    public DatosDetalleTopico(Topico topicoSaved) {
        this(topicoSaved.getId(), topicoSaved.getTitulo(), topicoSaved.getMensaje(), topicoSaved.getFechaCreacion(), topicoSaved.getCurso().getId(), topicoSaved.getAutor().getId());
    }
}
