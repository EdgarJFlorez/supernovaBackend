package com.supernova.inventario.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=false)
    private String rol; // "ADMIN", "USER"

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean enabled = true;   // 👈 ahora sí existe este campo

    @Column(name = "nombre")
    private String nombre; // opcional, lo tienes en la tabla

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // opcional, si quieres manejar timestamps
}
