package com.alura.foroHub.services;

import com.alura.foroHub.domain.usuarios.Usuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public Long getUserId(){
        var user = SecurityContextHolder.getContext().getAuthentication();

        if(user == null || !user.isAuthenticated()) throw new AccessDeniedException("No hay usuario autenticado");

        var principal = user.getPrincipal();

        Long idUser = null;
        if(principal instanceof Usuario){
            idUser = ((Usuario) principal).getId();
        }

        if(idUser == null) throw new AccessDeniedException("No se encontro un user autenticado");

        return idUser;
    }
}
