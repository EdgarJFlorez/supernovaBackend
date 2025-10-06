package com.supernova.inventario.service;

import com.supernova.inventario.dto.ProductoDTO;
import com.supernova.inventario.exception.RecursoNoEncontradoException;
import com.supernova.inventario.model.Categoria;
import com.supernova.inventario.model.Producto;
import com.supernova.inventario.repository.CategoriaRepository;
import com.supernova.inventario.repository.ProductoRepository;
import com.supernova.inventario.service.impl.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductoServiceImplTest {

    private ProductoRepository productoRepo;
    private CategoriaRepository categoriaRepo;
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        productoRepo = Mockito.mock(ProductoRepository.class);
        categoriaRepo = Mockito.mock(CategoriaRepository.class);
        productoService = new ProductoServiceImpl(productoRepo, categoriaRepo);
    }

    @Test
    void testListarSinFiltro() {
        Producto p1 = Producto.builder()
                .id(1L).nombre("Mouse").descripcion("Gamer")
                .stock(50).precio(BigDecimal.valueOf(25)).build();
        Producto p2 = Producto.builder()
                .id(2L).nombre("Teclado").descripcion("Mecánico")
                .stock(30).precio(BigDecimal.valueOf(45)).build();

        when(productoRepo.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<ProductoDTO> resultado = productoService.listar(null);

        assertEquals(2, resultado.size());
        assertEquals("Mouse", resultado.get(0).getNombre());
        assertEquals("Teclado", resultado.get(1).getNombre());
        verify(productoRepo, times(1)).findAll();
    }

    @Test
    void testListarConFiltro() {
        Producto p1 = Producto.builder()
                .id(3L).nombre("Monitor LG").descripcion("24 pulgadas")
                .stock(10).precio(BigDecimal.valueOf(300)).build();

        when(productoRepo.findByNombreContainingIgnoreCase("monitor"))
                .thenReturn(List.of(p1));

        List<ProductoDTO> resultado = productoService.listar("monitor");

        assertEquals(1, resultado.size());
        assertEquals("Monitor LG", resultado.get(0).getNombre());
        verify(productoRepo, times(1)).findByNombreContainingIgnoreCase("monitor");
    }

    @Test
    void testCrearProductoOk() {
        ProductoDTO dto = ProductoDTO.builder()
                .nombre("Laptop Lenovo")
                .descripcion("Core i7, 16GB RAM")
                .stock(5)
                .precio(BigDecimal.valueOf(1200))
                .categoriaId(1L)
                .build();

        Categoria categoria = Categoria.builder()
                .id(1L).nombre("Electrónica").build();

        Producto entidadGuardada = Producto.builder()
                .id(10L).nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .stock(dto.getStock())
                .precio(dto.getPrecio())
                .categoria(categoria)
                .build();

        when(categoriaRepo.findById(1L)).thenReturn(Optional.of(categoria));
        when(productoRepo.save(any(Producto.class))).thenReturn(entidadGuardada);

        ProductoDTO resultado = productoService.crear(dto);

        assertNotNull(resultado.getId());
        assertEquals(10L, resultado.getId());
        assertEquals("Laptop Lenovo", resultado.getNombre());
        verify(categoriaRepo, times(1)).findById(1L);
        verify(productoRepo, times(1)).save(any(Producto.class));
    }

    @Test
    void testCrearProductoCategoriaNoExiste() {
        ProductoDTO dto = ProductoDTO.builder()
                .nombre("Tablet")
                .descripcion("Samsung Galaxy")
                .stock(3)
                .precio(BigDecimal.valueOf(800))
                .categoriaId(99L)
                .build();

        when(categoriaRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> productoService.crear(dto));
        verify(productoRepo, never()).save(any());
    }
}
