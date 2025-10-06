package com.supernova.inventario;

import com.supernova.inventario.model.Usuario;
import com.supernova.inventario.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    // Seeder: crea el usuario admin al iniciar
    @Bean
    CommandLineRunner init(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByEmail("admin@supernova.com").isEmpty()) {
                Usuario u = Usuario.builder()
                        .email("admin@supernova.com")
                        .password(encoder.encode("123456"))
                        .rol("ADMIN")
                        .build();
                repo.save(u);
                System.out.println("✅ Usuario admin creado: admin@supernova.com / 123456");
            }
        };
    }
}
