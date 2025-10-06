package com.supernova.inventario.security;

import com.supernova.inventario.model.Usuario;
import com.supernova.inventario.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    public UsuarioDetailsService(UsuarioRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario u = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        // Si tu 'rol' ya viene con 'ROLE_' puedes usarlo directo; si no, prefija:
        String authority = u.getRol().startsWith("ROLE_") ? u.getRol() : "ROLE_" + u.getRol();

        return User.withUsername(u.getEmail())
                .password(u.getPassword())          // hash BCrypt almacenado
                .authorities(authority)
                .accountLocked(!Boolean.TRUE.equals(u.getEnabled()))
                .build();
    }
}
