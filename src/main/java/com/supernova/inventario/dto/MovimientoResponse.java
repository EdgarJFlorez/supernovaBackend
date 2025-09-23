package com.supernova.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovimientoResponse {
    private Long id;
    private Long productoId;
    private String tipo;
    private Integer cantidad;
    private String message;
}

