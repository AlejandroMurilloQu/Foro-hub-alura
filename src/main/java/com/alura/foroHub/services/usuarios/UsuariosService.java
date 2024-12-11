package com.alura.foroHub.services.usuarios;

import com.alura.foroHub.domain.ValidacionException;
import com.alura.foroHub.domain.usuarios.CreateUsuarioDTO;
import com.alura.foroHub.domain.usuarios.Usuario;
import com.alura.foroHub.domain.usuarios.UsuarioDto;
import com.alura.foroHub.domain.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository repository;

    public UsuarioDto registrar(CreateUsuarioDTO datos){

        var usuarioExists = repository.existsByCorreoElectronico(datos.correoElectronico());

        if(usuarioExists){
            throw new ValidacionException("Ya existe un usuario con este correo electronico");
        }

        Usuario createdUsuario = repository.save(new Usuario(datos));

        return new UsuarioDto(createdUsuario);
    }

    public UsuarioDto editar(Long id, CreateUsuarioDTO datos) {

        boolean existsUser = repository.existsById(id);
        boolean existsEmail = repository.existsByCorreoElectronicoAndIdNot(datos.correoElectronico(), id);

        if(!existsUser){
            throw new ValidacionException("No se encontro un usuario con ese id");
        }
        if(existsEmail){
            throw new ValidacionException("Ya existe una cuenta con ese email");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder(12);
        Usuario updatedUsuario = repository.getReferenceById(id);
        if(datos.contrasena() != null && !datos.contrasena().isBlank()) updatedUsuario.setContrasena(encoder.encode(datos.contrasena()));
        if(datos.correoElectronico() != null && !datos.correoElectronico().isBlank()) updatedUsuario.setCorreoElectronico(datos.correoElectronico());
        if(datos.nombre() != null && !datos.nombre().isBlank()) updatedUsuario.setNombre(datos.nombre());
        repository.save(updatedUsuario);
        return new UsuarioDto(updatedUsuario);
    }

    public void eliminar(Long id) {
        boolean existsUser = repository.existsById(id);

        if(!existsUser) throw new ValidacionException("No existe un usuario con ese id");
        
        repository.deleteById(id);
    }
}
