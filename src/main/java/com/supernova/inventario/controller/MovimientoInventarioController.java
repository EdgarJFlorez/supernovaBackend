package com.supernova.inventario.controller;

import com.supernova.inventario.dto.MovimientoDTO;
import com.supernova.inventario.dto.MovimientoResponse;
import com.supernova.inventario.model.MovimientoInventario;
import com.supernova.inventario.service.MovimientoInventarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoInventarioController {

    private final MovimientoInventarioService service;

    public MovimientoInventarioController(MovimientoInventarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse crear(@Valid @RequestBody MovimientoDTO dto) {
        MovimientoInventario mov = service.registrar(dto);
        return new MovimientoResponse(
                mov.getId(),
                mov.getProducto().getId(),
                mov.getTipo().name(),
                mov.getCantidad(),
                "Movimiento registrado"
        );
    }
}


