package com.alura.foroHub.domain.topicos;

import com.alura.foroHub.domain.cursos.Curso;
import com.alura.foroHub.domain.respuestas.Respuesta;
import com.alura.foroHub.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Getter
    private String mensaje;

    @Column(name = "fechacreacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso")
    private Curso curso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor")
    private Usuario autor;


    @OneToMany(mappedBy = "topico", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Transient
    private List<Respuesta> respuestas;

    public Topico(DatosRegistroTopico datos, Curso curso, Usuario usuario) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.status = Status.OPEN;
        this.fechaCreacion = LocalDateTime.now();
        this.curso = curso;
        this.autor = usuario;
    }
}
