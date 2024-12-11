package com.alura.foroHub.controller;

import com.alura.foroHub.domain.usuarios.CreateUsuarioDTO;
import com.alura.foroHub.domain.usuarios.UsuarioDto;
import com.alura.foroHub.domain.usuarios.UsuariosRepository;
import com.alura.foroHub.services.usuarios.UsuariosService;
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
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosRepository repository;
    @Autowired
    private UsuariosService service;

    @GetMapping
    @Transactional
    public ResponseEntity<Page<UsuarioDto>> listarUsuarios(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        var usuarios = repository.findAll(pageable).map(UsuarioDto::new);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UsuarioDto> crearUsuario(@RequestBody @Valid CreateUsuarioDTO datos, UriComponentsBuilder uriComponentsBuilder){
        UsuarioDto createdUsuario = service.registrar(datos);
        URI url = UriComponentsBuilder.fromPath("/{id}").buildAndExpand(createdUsuario.id()).toUri();
        return ResponseEntity.created(url).body(createdUsuario);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UsuarioDto> editarUsuario(@RequestBody CreateUsuarioDTO datos, @PathVariable Long id){
        UsuarioDto updatedUsuario = service.editar(id,datos);
        return ResponseEntity.ok(updatedUsuario);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, String>> eliminarUsuario(@PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.ok(Map.of("Message", "Se ha eliminado el usuario"));
    }
}
