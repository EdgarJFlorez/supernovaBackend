package com.supernova.inventario.security;

import com.supernova.inventario.model.Usuario;
import com.supernova.inventario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDetailsServiceTest {

    @Test
    void loadUserByUsernameOk() {
        UsuarioRepository repo = Mockito.mock(UsuarioRepository.class);

        Usuario usuario = Usuario.builder()
                .email("admin@supernova.com")
                .password("hashedpassword")
                .rol("ADMIN")
                .enabled(true)
                .build();

        // Simulamos que el repo encuentra el usuario
        Mockito.when(repo.findByEmail("admin@supernova.com")).thenReturn(Optional.of(usuario));

        UsuarioDetailsService service = new UsuarioDetailsService(repo);

        UserDetails details = service.loadUserByUsername("admin@supernova.com");

        // Validaciones
        assertEquals("admin@supernova.com", details.getUsername());
        assertEquals("hashedpassword", details.getPassword());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsernameFail() {
        UsuarioRepository repo = Mockito.mock(UsuarioRepository.class);

        // Simulamos que el repo NO encuentra el usuario
        Mockito.when(repo.findByEmail("inexistente@correo.com")).thenReturn(Optional.empty());

        UsuarioDetailsService service = new UsuarioDetailsService(repo);

        // Debe lanzar excepción
        assertThrows(UsernameNotFoundException.class, () ->
                service.loadUserByUsername("inexistente@correo.com"));
    }
}
