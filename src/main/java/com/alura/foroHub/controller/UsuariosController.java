package com.alura.foroHub.controller;

import com.alura.foroHub.domain.usuarios.CreateUsuarioDTO;
import com.alura.foroHub.domain.usuarios.UsuarioDto;
import com.alura.foroHub.domain.usuarios.UsuariosRepository;
import com.alura.foroHub.services.usuarios.UsuariosService;
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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
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
@Tag(name = "Usuarios")
@SecurityRequirement(name = "Bearer Authentication")
public class UsuariosController {

    @Autowired
    private UsuariosRepository repository;
    @Autowired
    private UsuariosService service;

    @GetMapping
    @Transactional
    @Operation(summary = "Obtener lista de todos los usuarios registrados")
    public ResponseEntity<Page<UsuarioDto>> listarUsuarios(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) @Parameter(hidden = true) Pageable pageable){
        var usuarios = repository.findAll(pageable).map(UsuarioDto::new);
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Editar un usuario")
    public ResponseEntity<UsuarioDto> editarUsuario(@RequestBody CreateUsuarioDTO datos, @Parameter(name = "id", description = "Id del usuario a editar") @PathVariable Long id){
        UsuarioDto updatedUsuario = service.editar(id,datos);
        return ResponseEntity.ok(updatedUsuario);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar un usuario",
            responses = {@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "object", properties = {@StringToClassMapItem(key = "Message", value = String.class)})))})
    public ResponseEntity<Map<String, String>> eliminarUsuario(@PathVariable @Parameter(name = "id", description = "Id del usuario a eliminar") Long id){
        service.eliminar(id);
        return ResponseEntity.ok(Map.of("Message", "Se ha eliminado el usuario"));
    }
}
