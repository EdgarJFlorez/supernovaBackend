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
        List<Producto> productos = (q == null || q.isBlank())
                ? productoRepo.findAll()
                : productoRepo.findByNombreContainingIgnoreCase(q);

        return productos.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public ProductoDTO crear(ProductoDTO dto) {
        Categoria categoria = categoriaRepo.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada"));

        Producto producto = Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .stock(dto.getStock())
                .precio(dto.getPrecio())
                .categoria(categoria)
                .build();

        return mapToDTO(productoRepo.save(producto));
    }

    @Override
    public ProductoDTO actualizar(ProductoDTO dto) {
        Producto producto = productoRepo.findById(dto.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setStock(dto.getStock());
        producto.setPrecio(dto.getPrecio());

        return mapToDTO(productoRepo.save(producto));
    }

    @Override
    public void eliminar(Long id) {
        if (!productoRepo.existsById(id)) {
            throw new RecursoNoEncontradoException("Producto no encontrado");
        }
        productoRepo.deleteById(id);
    }

    private ProductoDTO mapToDTO(Producto p) {
        return ProductoDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .stock(p.getStock())
                .precio(p.getPrecio())
                .categoriaId(p.getCategoria() != null ? p.getCategoria().getId() : null)
                .categoriaNombre(p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría")
                .build();
    }
}


