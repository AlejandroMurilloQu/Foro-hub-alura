package com.alura.foroHub.controller;

import com.alura.foroHub.domain.topicos.*;
import com.alura.foroHub.services.topicos.RegistrarTopico;
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

//0657636a-30f9-48a6-8ded-70f6cc767218
@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private RegistrarTopico topicosService;

    @Autowired
    private TopicosRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder){
        DatosDetalleTopico response = topicosService.save(datos);

        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    @GetMapping
    @Transactional
    public ResponseEntity<Page<DatosDetalleTopico>> obtenerTopicos(@PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion){
        Page<DatosDetalleTopico> topicos = repository.findAll(paginacion).map(DatosDetalleTopico::new);
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleTopicoGet> obtenerTopicoPorId(@PathVariable Long id){
        DatosDetalleTopicoGet datos = topicosService.getById(id);
        return ResponseEntity.ok(datos);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosDetalleTopico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosRegistroTopico datos){
        DatosDetalleTopico response = topicosService.actualizar(id, datos);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        topicosService.delete(id);
        return ResponseEntity.ok().build();
    }

}
