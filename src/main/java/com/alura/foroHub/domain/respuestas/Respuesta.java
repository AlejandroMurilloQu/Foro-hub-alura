package com.alura.foroHub.domain.respuestas;

import com.alura.foroHub.domain.topicos.Topico;
import com.alura.foroHub.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    @ManyToOne
    private Topico topico;

    private LocalDateTime fechaCreacion;

    @ManyToOne(cascade = CascadeType.ALL)
    private Usuario autor;

    private String solucion;
}
