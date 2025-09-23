package com.supernova.inventario.service.impl;

import com.supernova.inventario.dto.ProductoDTO;
import com.supernova.inventario.exception.RecursoNoEncontradoException;
import com.supernova.inventario.model.Categoria;
import com.supernova.inventario.model.Producto;
import com.supernova.inventario.repository.CategoriaRepository;
import com.supernova.inventario.repository.ProductoRepository;
import com.supernova.inventario.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;

    @Override
    public List<ProductoDTO> listar(String q) {
        var lista = (q == null || q.isBlank())
                ? productoRepo.findAll()
                : productoRepo.findByNombreContainingIgnoreCase(q);

        return lista.stream().map(p -> ProductoDTO.builder()
                        .id(p.getId())
                        .nombre(p.getNombre())
                        .descripcion(p.getDescripcion())
                        .stock(p.getStock())
                        .precio(p.getPrecio())
                        .categoriaId(p.getCategoria() != null ? p.getCategoria().getId() : null)
                        .build())
                .toList();
    }

    @Override
    public ProductoDTO crear(ProductoDTO dto) {
        Categoria cat = categoriaRepo.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada"));

        Producto p = Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .stock(dto.getStock())
                .precio(dto.getPrecio())
                .categoria(cat)
                .build();

        p = productoRepo.save(p);
        dto.setId(p.getId());
        return dto;
    }
}

