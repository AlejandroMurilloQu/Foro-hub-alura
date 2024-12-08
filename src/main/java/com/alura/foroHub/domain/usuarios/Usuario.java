package com.alura.foroHub.domain.usuarios;

import com.alura.foroHub.domain.perfiles.Perfil;
import com.alura.foroHub.domain.respuestas.Respuesta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "correoelectronico")
    private String correoElectronico;

    private String contrasena;

    @OneToMany(mappedBy = "autor")
    @Transient
    private List<Respuesta> respuestas;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "perfil_usuario", joinColumns = @JoinColumn(name = "id_perfil"), inverseJoinColumns = @JoinColumn(name = "id_usuario"))
    private List<Perfil> perfiles;
}
