package com.alura.foroHub;

import com.alura.foroHub.domain.usuarios.Usuario;
import com.alura.foroHub.services.topicos.RegistrarTopico;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.util.List;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Foro Hub", description = "Endpoints para la Api Rest de Foro Hub como parte del desafio de Alura Latam", version = "1.0"))
@SecurityScheme(
		name = "Bearer Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer",
		description = "Token que se obtiene al iniciar sesion"
)
public class ForoHubApplication {

	public static void main(String[] args) {

		SpringApplication.run(ForoHubApplication.class, args);
	}

}
