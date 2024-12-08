package com.alura.foroHub.domain.topicos.validaciones;

import com.alura.foroHub.domain.ValidacionException;
import com.alura.foroHub.domain.topicos.DatosRegistroTopico;
import com.alura.foroHub.domain.topicos.TopicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoExists implements TopicosValidation {

    @Autowired
    private TopicosRepository repository;

    @Override
    public void validar(DatosRegistroTopico datos) {
        boolean topicoExists = repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());

        if (topicoExists) {
            throw new ValidacionException("Ya existe un topico con ese titulo y mensaje");
        }
    }
}
