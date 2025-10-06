package com.supernova.inventario.config;

import com.supernova.inventario.model.Usuario;
import com.supernova.inventario.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    public Runnable seedAdmin(UsuarioRepository repo, PasswordEncoder encoder) {
        return () -> {
            repo.findByEmail("admin@supernova.com").orElseGet(() -> {
                Usuario u = new Usuario();
                u.setEmail("admin@supernova.com");
                u.setPassword(encoder.encode("123456")); // <— encripta aquí
                u.setNombre("Administrador");
                u.setRol("ADMIN");       // ajusta si usas enum (p.ej. Rol.ADMIN.name())
                u.setEnabled(true);
                Usuario saved = repo.save(u);
                System.out.println("✔ Usuario admin creado: " + saved.getEmail());
                return saved;
            });
        };
    }
}
