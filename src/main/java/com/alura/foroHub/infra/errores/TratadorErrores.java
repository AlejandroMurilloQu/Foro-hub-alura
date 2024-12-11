package com.alura.foroHub.infra.errores;

import com.alura.foroHub.domain.TokenException;
import com.alura.foroHub.domain.ValidacionException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;
import java.util.*;

@RestControllerAdvice
public class TratadorErrores {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleBadBodyRequest(HttpMessageNotReadableException e){
        Map<String, String> errorResponse = new HashMap<>();
        if(e.getCause() instanceof InvalidFormatException){
            InvalidFormatException cause = (InvalidFormatException) e.getCause();
            String campo = "";
            if(!cause.getPath().isEmpty()){
                campo = cause.getPath().get(0).getFieldName();
            }
            errorResponse.put("Campo", campo);
            errorResponse.put("Valor Incorrecto", cause.getValue().toString());
            errorResponse.put("Message", "Tipo de dato inválido. Se esperaba un valor de tipo '" +
                    cause.getTargetType().getSimpleName() + "'");
        }else{
            errorResponse.put("message", "Cuerpo de la solicitud no legible o inválido");
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseValidacionError> handleValidationError(MethodArgumentNotValidException e){

        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(new ResponseValidacionError("Los datos enviados son incorrectos", errores));
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<Map<String, String>> handleNoToken(TokenException e){
        return ResponseEntity.status(403).body(Map.of("Error", e.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleTokenInvalido(BadCredentialsException e){
        return ResponseEntity.status(403).body(Map.of("Error", "Fallo el inicio de sesión"));
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrorRepository(ValidacionException e){
        return ResponseEntity.badRequest().body(Map.of("Message", "Ha ocurrido un error validando los datos","error", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleBadParams(MethodArgumentTypeMismatchException e){
        return ResponseEntity.badRequest().body(Map.of("Message", "Error en los parametros enviados en la URL","Campo", Objects.requireNonNull(e.getParameter().getParameterName()), "Valor", Objects.requireNonNull(e.getValue()).toString()));
    }

    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }

    private record ResponseValidacionError(String mensaje, List<DatosErrorValidacion> errores){
    }
}
