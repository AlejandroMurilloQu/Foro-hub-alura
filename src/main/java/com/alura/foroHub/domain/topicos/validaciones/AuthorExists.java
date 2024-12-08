package com.alura.foroHub.domain.topicos.validaciones;

import com.alura.foroHub.domain.ValidacionException;
import com.alura.foroHub.domain.topicos.DatosRegistroTopico;
import com.alura.foroHub.domain.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorExists implements TopicosValidation{

    @Autowired
    private UsuariosRepository repository;

    @Override
    public void validar(DatosRegistroTopico datos) {
        boolean authorExists = repository.existsById(datos.autor());

        if(!authorExists){
            throw new ValidacionException("No existe un usuario con ese id");
        }
    }
}
