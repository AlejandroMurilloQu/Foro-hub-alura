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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/respuestas")
@Tag(name = "Respuestas")
@SecurityRequirement(name = "Bearer Authentication")
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
    @Operation(summary = "Obtener las respuestas de un topico")
    public ResponseEntity<Page<RespuestaResponseDTO>> getRespuestas(@PathVariable @Parameter(name = "id", description = "Id del topico") Long id, @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) @Parameter(hidden = true) Pageable pageable) {

        Page<RespuestaResponseDTO> respuestas = service.obtenerRespuestas(id, pageable).map(RespuestaResponseDTO::new);

        return ResponseEntity.ok(respuestas);
    }

    //OBTENER RESPUESTAS DE UN USUARIO
    @GetMapping("/user/{id}")
    @Transactional
    @Operation(summary = "Obtener las respuestas de un usuario")
    public ResponseEntity<Page<RespuestaResponseDTO>> getRespuestasByUser(@PathVariable @Parameter(name = "id", description = "Id del usuario") Long id, @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.DESC) @Parameter(hidden = true)  Pageable pageable){
        Page<RespuestaResponseDTO> respuestas = service.obtenerRespuestasByUser(id, pageable).map(RespuestaResponseDTO::new);
        return ResponseEntity.ok(respuestas);
    }

    @PostMapping("/{id}")
    @Transactional
    @Operation(summary = "Registrar una respuesta en un topico")
    public ResponseEntity<RespuestaResponseDTO> registrarRespuesta(@PathVariable @Parameter(name = "id", description = "Id del topico donde se va a registrar la respuesta") Long id, @RequestBody @Valid CreateRespuestaDTO datos, UriComponentsBuilder uriComponentsBuilder) {
        Respuesta respuesta = service.registrar(id, datos);
        URI url = UriComponentsBuilder.fromPath("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(url).body(new RespuestaResponseDTO(respuesta));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Editar una respuesta")
    public ResponseEntity<RespuestaResponseDTO> editarRespuesta(@PathVariable @Parameter(name = "id", description = "Id de la respuesta a editar") Long id, @RequestBody @Valid CreateRespuestaDTO datos){
        Respuesta respuesta = service.editar(id, datos);

        return ResponseEntity.ok(new RespuestaResponseDTO(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar una respuesta", responses = {@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", properties = @StringToClassMapItem(key = "Message", value = String.class))))})
    public ResponseEntity<Map<String, String>> eliminarRespuesta(@PathVariable  @Parameter(name = "id", description = "Id de la respuesta a eliminar") Long id){
        service.eliminar(id);
        return ResponseEntity.ok(Map.of("Message", "Se ha eliminado la respuesta"));
    }
}
