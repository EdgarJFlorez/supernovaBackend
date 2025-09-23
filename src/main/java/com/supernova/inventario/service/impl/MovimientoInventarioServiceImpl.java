package com.supernova.inventario.service.impl;

import com.supernova.inventario.dto.MovimientoDTO;
import com.supernova.inventario.exception.RecursoNoEncontradoException;
import com.supernova.inventario.model.MovimientoInventario;
import com.supernova.inventario.model.MovimientoTipo;
import com.supernova.inventario.model.Producto;
import com.supernova.inventario.repository.MovimientoInventarioRepository;
import com.supernova.inventario.repository.ProductoRepository;
import com.supernova.inventario.service.MovimientoInventarioService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final ProductoRepository productoRepository;
    private final MovimientoInventarioRepository movimientoRepository;

    public MovimientoInventarioServiceImpl(ProductoRepository productoRepository,
                                           MovimientoInventarioRepository movimientoRepository) {
        this.productoRepository = productoRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Override
    @jakarta.transaction.Transactional
    public MovimientoInventario registrar(MovimientoDTO dto) {
        // 1) Buscar producto
        Producto producto = productoRepository.findByIdForUpdate(dto.getProductoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado: " + dto.getProductoId()));

        // 2) Calcular nuevo stock según tipo
        int actual = (producto.getStock() == null) ? 0 : producto.getStock();
        int nuevo;
        switch (dto.getTipo()) {
            case ALTA -> nuevo = actual + dto.getCantidad();
            case BAJA, VENTA, REPARACION -> nuevo = actual - dto.getCantidad();
            default -> throw new IllegalArgumentException("Tipo de movimiento no soportado: " + dto.getTipo());
        }
        if (nuevo < 0) {
            // tu excepción de negocio
            throw new com.supernova.inventario.exception.ReglaNegocioException("Stock insuficiente para realizar la operación");
        }

        // 3) Guardar nuevo stock
        producto.setStock(nuevo);
        productoRepository.save(producto);

        // 4) Crear y guardar el movimiento (sin setFecha: lo pone @CreationTimestamp)
        MovimientoInventario mov = MovimientoInventario.builder()
                .producto(producto)
                .tipo(dto.getTipo())
                .cantidad(dto.getCantidad())
                .comentario(dto.getComentario())
                .build();

        return movimientoRepository.save(mov);
    }
}
