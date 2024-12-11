package com.alura.foroHub.domain.respuestas;

import com.alura.foroHub.domain.topicos.Topico;
import com.alura.foroHub.domain.usuarios.CreateUsuarioDTO;
import com.alura.foroHub.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    @Column(name = "topico")
    private Long topico;

    @Column(name = "fechacreacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "autor")
    private Long autor;

    private String solucion;


    public Respuesta(Long topico, Long usuario, CreateRespuestaDTO datos) {
        this.mensaje = datos.mensaje();
        this.topico = topico;
        this.autor = usuario;
        this.fechaCreacion = LocalDateTime.now();
    }
}
