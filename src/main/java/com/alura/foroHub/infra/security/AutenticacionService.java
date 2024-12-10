package com.alura.foroHub.infra.security;

import com.alura.foroHub.domain.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacionService implements UserDetailsService {

    @Autowired
    private UsuariosRepository repository;
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        return repository.findByCorreoElectronico(correo);
    }
}
