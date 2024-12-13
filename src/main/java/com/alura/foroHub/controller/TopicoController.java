package com.alura.foroHub.controller;

import com.alura.foroHub.domain.topicos.*;
import com.alura.foroHub.services.topicos.RegistrarTopico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Transient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

//0657636a-30f9-48a6-8ded-70f6cc767218
@RestController
@RequestMapping("/topicos")
@Tag(name = "Topicos")
@SecurityRequirement(name = "Bearer Authentication")
public class TopicoController {

    @Autowired
    private RegistrarTopico topicosService;

    @Autowired
    private TopicosRepository repository;

    @PostMapping
    @Transactional
    @Operation(summary = "Publicar un topico")
    public ResponseEntity<DatosDetalleTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder){
        DatosDetalleTopico response = topicosService.save(datos);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @GetMapping
    @Transactional
    @Operation(summary = "Obtener una lista de todos los topicos")
    public ResponseEntity<Page<DatosDetalleTopico>> obtenerTopicos(@PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) @Parameter(hidden = true) Pageable paginacion){
        Page<DatosDetalleTopico> topicos = repository.findAll(paginacion).map(DatosDetalleTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    @Transactional
    @Operation(summary = "Obtener topico por id")
    public ResponseEntity<DatosDetalleTopicoGet> obtenerTopicoPorId(@PathVariable @Parameter(name = "id", description = "Id del topico")  Long id){
        DatosDetalleTopicoGet datos = topicosService.getById(id);
        return ResponseEntity.ok(datos);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualizar topico")
    public ResponseEntity<DatosDetalleTopico> actualizarTopico(@PathVariable @Parameter(name = "id", description = "Id del topico a actualizar") Long id, @RequestBody @Valid DatosRegistroTopico datos){
        DatosDetalleTopico response = topicosService.actualizar(id, datos);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar topico", responses = {@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(type = "object", properties = {@StringToClassMapItem(key = "Message", value = String.class)})))})
    public ResponseEntity<Map<String, String>> eliminarTopico(@PathVariable @Parameter(name = "id", description = "Id del topico a eliminar") Long id){
        topicosService.delete(id);
        return ResponseEntity.ok(Map.of("Message", "Se ha eliminado el topico"));
    }

}
