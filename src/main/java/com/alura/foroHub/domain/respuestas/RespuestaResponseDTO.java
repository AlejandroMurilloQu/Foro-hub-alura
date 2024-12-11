package com.alura.foroHub.domain.respuestas;

import java.time.LocalDateTime;

public record RespuestaResponseDTO(Long id, String mensaje, Long topico_id, Long autor_id, LocalDateTime fecha_creacion, String solucion) {

    public RespuestaResponseDTO(Respuesta respuesta){
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico(), respuesta.getAutor(), respuesta.getFechaCreacion(), respuesta.getSolucion());
    }
}
