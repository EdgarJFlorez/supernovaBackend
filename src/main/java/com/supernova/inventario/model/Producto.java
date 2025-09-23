package com.supernova.inventario.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "producto")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    private String descripcion;

    // Usamos 'stock' como columna real en DB
    @Column(name = "stock", nullable = false)
    @Builder.Default
    private Integer stock = 0;

    @Builder.Default
    private BigDecimal precio = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /* ===== Aliases para compatibilidad con el servicio =====
       El servicio usa getStockActual()/setStockActual(...)
       Aquí los mapeamos al campo 'stock' que ya tienes.
    */
    public Integer getStockActual() {
        // Evita NPE si por alguna razón viene nulo desde DB
        return stock == null ? 0 : stock;
    }

    public void setStockActual(Integer nuevoStock) {
        this.stock = nuevoStock;
    }
}


