package com.alura.foroHub.services.topicos;

import com.alura.foroHub.domain.ValidacionException;
import com.alura.foroHub.domain.cursos.Curso;
import com.alura.foroHub.domain.cursos.CursosRepository;
import com.alura.foroHub.domain.topicos.*;
import com.alura.foroHub.domain.topicos.validaciones.TopicosValidation;
import com.alura.foroHub.domain.usuarios.Usuario;
import com.alura.foroHub.domain.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrarTopico {

    @Autowired
    private TopicosRepository repository;

    @Autowired
    private CursosRepository cursosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private List<TopicosValidation> validaciones;

    public DatosDetalleTopico save(DatosRegistroTopico datos) {
        validaciones.forEach(v -> v.validar(datos));

        Usuario autor = usuariosRepository.getReferenceById(datos.autor());
        Curso curso = cursosRepository.getReferenceById(datos.curso());

        Topico topico = repository.save(new Topico(datos, curso, autor));
        return new DatosDetalleTopico(topico);
    }

    public DatosDetalleTopicoGet getById(Long id){
        Optional<Topico> topico = repository.findById(id);

        if(topico.isEmpty()){
            throw new ValidacionException("No existe un topico con ese id");
        }

        return new DatosDetalleTopicoGet(topico.get());
    }

    public DatosDetalleTopico actualizar(Long id, DatosRegistroTopico datos) {
        Optional<Topico> topicoExist = repository.findById(id);

        if(topicoExist.isEmpty()){
            throw new ValidacionException("No existe un topico con ese id");
        }

        validaciones.forEach(v -> v.validar(datos));

        Topico topico = topicoExist.get();
        topico.setTitulo(datos.titulo());
        topico.setMensaje(datos.mensaje());
        Usuario autor = usuariosRepository.getReferenceById(datos.autor());
        Curso curso = cursosRepository.getReferenceById(datos.curso());
        topico.setAutor(autor);
        topico.setCurso(curso);

        return new DatosDetalleTopico(repository.save(topico));

    }

    public void delete(Long id) {
        Optional<Topico> topicoExist = repository.findById(id);

        if(topicoExist.isEmpty()){
            throw new ValidacionException("No existe un topico con ese id");
        }

        repository.deleteById(id);
    }
}
