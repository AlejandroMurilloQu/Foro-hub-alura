package com.alura.foroHub.controller;

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
import com.alura.foroHub.services.respuestas.RespuestasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/respuestas")
public class RespuestasController {


    @Autowired
    private RespuestasRepository repository;

    @Autowired
    private RespuestasService service;


    @Autowired
    private AuthService authService;

    //OBTENER RESPUESTAS DE UN TOPICO
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<Page<RespuestaResponseDTO>> getRespuestas(@PathVariable Long id, @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<RespuestaResponseDTO> respuestas = service.obtenerRespuestas(id, pageable).map(RespuestaResponseDTO::new);

        return ResponseEntity.ok(respuestas);
    }

    //OBTENER RESPUESTAS DE UN USUARIO
    @GetMapping("/user/{id}")
    @Transactional
    public ResponseEntity<Page<RespuestaResponseDTO>> getRespuestasByUser(@PathVariable Long id, @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) Pageable pageable){
        Page<RespuestaResponseDTO> respuestas = service.obtenerRespuestasByUser(id, pageable).map(RespuestaResponseDTO::new);
        return ResponseEntity.ok(respuestas);
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<RespuestaResponseDTO> registrarRespuesta(@PathVariable Long id, @RequestBody @Valid CreateRespuestaDTO datos, UriComponentsBuilder uriComponentsBuilder) {
        Respuesta respuesta = service.registrar(id, datos);
        URI url = UriComponentsBuilder.fromPath("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(new RespuestaResponseDTO(respuesta));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RespuestaResponseDTO> editarRespuesta(@PathVariable Long id, @RequestBody @Valid CreateRespuestaDTO datos){
        Respuesta respuesta = service.editar(id, datos);

        return ResponseEntity.ok(new RespuestaResponseDTO(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, String>> eliminarRespuesta(@PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.ok(Map.of("Message", "Se ha eliminado la respuesta"));
    }
}
