package com.alura.foroHub.controller;

import com.alura.foroHub.domain.usuarios.AuthUsuariosDTO;
import com.alura.foroHub.domain.usuarios.CreateUsuarioDTO;
import com.alura.foroHub.domain.usuarios.Usuario;
import com.alura.foroHub.domain.usuarios.UsuarioDto;
import com.alura.foroHub.infra.security.TokenService;
import com.alura.foroHub.services.usuarios.UsuariosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@Tag(name = "Authentication")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuariosService service;

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesion", responses = {@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json",schema = @Schema(type = "object", properties = @StringToClassMapItem(key = "token", value = String.class))))})
    public ResponseEntity<Map<String,String>> login(@RequestBody @Valid AuthUsuariosDTO datos){
        Authentication authToken = new UsernamePasswordAuthenticationToken(datos.email(), datos.password());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        Usuario usuario = (Usuario)usuarioAutenticado.getPrincipal();
        if(usuario == null){
            return ResponseEntity.notFound().build();
        }
        var token = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    @Transactional
    @Operation(summary = "Crear un usuario")
    public ResponseEntity<UsuarioDto> crearUsuario(@RequestBody @Valid CreateUsuarioDTO datos, UriComponentsBuilder uriComponentsBuilder){
        UsuarioDto createdUsuario = service.registrar(datos);
        URI url = UriComponentsBuilder.fromPath("/{id}").buildAndExpand(createdUsuario.id()).toUri();
        return ResponseEntity.created(url).body(createdUsuario);
    }
}
