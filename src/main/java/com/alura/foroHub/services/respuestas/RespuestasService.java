package com.alura.foroHub.services.respuestas;

import com.alura.foroHub.domain.ValidacionException;
import com.alura.foroHub.domain.respuestas.CreateRespuestaDTO;
import com.alura.foroHub.domain.respuestas.Respuesta;
import com.alura.foroHub.domain.respuestas.RespuestaResponseDTO;
import com.alura.foroHub.domain.respuestas.RespuestasRepository;
import com.alura.foroHub.domain.topicos.Topico;
import com.alura.foroHub.domain.topicos.TopicosRepository;
import com.alura.foroHub.domain.usuarios.Usuario;
import com.alura.foroHub.domain.usuarios.UsuariosRepository;
import com.alura.foroHub.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RespuestasService {

    @Autowired
    private RespuestasRepository repository;

    @Autowired
    private TopicosRepository topicosRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private AuthService authService;

    public Respuesta registrar(Long id, CreateRespuestaDTO datos) {
        boolean topicoExists = topicosRepository.existsById(id);

        if (!topicoExists) throw new ValidacionException("No existe un topico con ese id");

        Long idUser = authService.getUserId();

        return repository.save(new Respuesta(id, idUser, datos));
    }

    public Page<Respuesta> obtenerRespuestas(Long id, Pageable pageable) {

        boolean topicoExists = topicosRepository.existsById(id);

        if (!topicoExists) throw new ValidacionException("No existe un topico con ese id");

        return repository.findByTopicoId(id, pageable);
    }

    public Page<Respuesta> obtenerRespuestasByUser(Long id, Pageable pageable) {
        boolean userExists = usuariosRepository.existsById(id);

        if (!userExists) throw new ValidacionException("No existe un usuario con ese id");

        return repository.findByAutor(id, pageable);
    }

    public Respuesta editar(Long id, CreateRespuestaDTO datos) {
        boolean respuestaExists = repository.existsById(id);

        if(!respuestaExists) throw new ValidacionException("No existe una respuesta con ese id");

        Respuesta respuesta = repository.getReferenceById(id);
        respuesta.setMensaje(datos.mensaje());
        return repository.save(respuesta);
    }

    public void eliminar(Long id) {
        boolean respuestaExists = repository.existsById(id);
        if(!respuestaExists) throw new ValidacionException("No existe una respuesta con ese id");
        repository.deleteById(id);
    }
}
