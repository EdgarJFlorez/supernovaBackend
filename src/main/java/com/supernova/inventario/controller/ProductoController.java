package com.supernova.inventario.controller;

import com.supernova.inventario.dto.ProductoDTO;
import com.supernova.inventario.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public List<ProductoDTO> listar(@RequestParam(required = false, name = "q") String q) {
        return service.listar(q);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoDTO crear(@RequestBody ProductoDTO dto) {
        return service.crear(dto);
    }
}

