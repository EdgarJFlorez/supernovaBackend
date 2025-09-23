package com.supernova.inventario.controller;

import com.supernova.inventario.dto.CategoriaDTO;
import com.supernova.inventario.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    public List<CategoriaDTO> listar() { return service.listar(); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaDTO crear(@RequestBody CategoriaDTO dto) { return service.crear(dto); }
}
