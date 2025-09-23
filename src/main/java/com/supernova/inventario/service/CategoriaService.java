package com.supernova.inventario.service;

import com.supernova.inventario.dto.CategoriaDTO;
import java.util.List;

public interface CategoriaService {
    List<CategoriaDTO> listar();
    CategoriaDTO crear(CategoriaDTO dto);
}
