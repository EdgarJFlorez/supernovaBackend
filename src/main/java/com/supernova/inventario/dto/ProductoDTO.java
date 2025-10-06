package com.supernova.inventario.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer stock;
    private BigDecimal precio;
    private Long categoriaId;
    private String categoriaNombre; // 🆕 Nombre de la categoría
}

