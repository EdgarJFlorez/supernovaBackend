package com.supernova.inventario.dto;

import com.supernova.inventario.model.MovimientoTipo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MovimientoDTO {

    @NotNull(message = "productoId es obligatorio")
    private Long productoId;

    @NotNull(message = "tipo es obligatorio")
    private MovimientoTipo tipo;

    @Min(value = 1, message = "cantidad debe ser >= 1")
    private int cantidad;

    // OPCIONAL
    private String comentario;

    // ---- getters/setters ----
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public MovimientoTipo getTipo() { return tipo; }
    public void setTipo(MovimientoTipo tipo) { this.tipo = tipo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}


