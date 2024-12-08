package com.alura.foroHub.domain.topicos.validaciones;

import com.alura.foroHub.domain.ValidacionException;
import com.alura.foroHub.domain.cursos.CursosRepository;
import com.alura.foroHub.domain.topicos.DatosRegistroTopico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CursoExists implements TopicosValidation{

    @Autowired
    private CursosRepository repository;
    @Override
    public void validar(DatosRegistroTopico datos) {
        boolean cursoExists = repository.existsById(datos.curso());

        if(!cursoExists){
            throw new ValidacionException("No existe un curso con ese id");
        }
    }
}
