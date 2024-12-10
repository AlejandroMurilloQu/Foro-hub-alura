package com.alura.foroHub.controller;

import com.alura.foroHub.domain.usuarios.AuthUsuariosDTO;
import com.alura.foroHub.domain.usuarios.Usuario;
import com.alura.foroHub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
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
}
