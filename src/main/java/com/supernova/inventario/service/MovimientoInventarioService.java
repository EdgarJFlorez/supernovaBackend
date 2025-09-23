package com.supernova.inventario.service;

import com.supernova.inventario.dto.MovimientoDTO;
import com.supernova.inventario.model.MovimientoInventario;

public interface MovimientoInventarioService {
    MovimientoInventario registrar(MovimientoDTO dto);
}

