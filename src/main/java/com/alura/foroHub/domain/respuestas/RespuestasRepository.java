package com.alura.foroHub.domain.respuestas;

import com.alura.foroHub.domain.topicos.Topico;
import com.alura.foroHub.domain.usuarios.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespuestasRepository extends JpaRepository<Respuesta, Long> {

    @Query("SELECT r from Respuesta r WHERE r.topico = :topico")
    Page<Respuesta> findByTopicoId(Long topico, Pageable pageable);

    Page<Respuesta> findByAutor(Long autor, Pageable pageable);
}
