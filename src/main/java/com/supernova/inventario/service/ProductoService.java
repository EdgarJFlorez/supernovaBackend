package com.supernova.inventario.service;

import com.supernova.inventario.dto.ProductoDTO;
import java.util.List;

public interface ProductoService {
    List<ProductoDTO> listar(String q);
    ProductoDTO crear(ProductoDTO dto);
}
