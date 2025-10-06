package com.supernova.inventario.service;

import com.supernova.inventario.dto.CategoriaDTO;
import com.supernova.inventario.exception.ReglaNegocioException;
import com.supernova.inventario.model.Categoria;
import com.supernova.inventario.repository.CategoriaRepository;
import com.supernova.inventario.service.impl.CategoriaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoriaServiceImplTest {

    private CategoriaRepository categoriaRepo;
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        categoriaRepo = Mockito.mock(CategoriaRepository.class);
        categoriaService = new CategoriaServiceImpl(categoriaRepo);
    }

    @Test
    void testListarCategorias() {
        Categoria c1 = Categoria.builder().id(1L).nombre("Electrónica").descripcion("Dispositivos").build();
        Categoria c2 = Categoria.builder().id(2L).nombre("Ropa").descripcion("Vestimenta").build();

        when(categoriaRepo.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<CategoriaDTO> resultado = categoriaService.listar();

        assertEquals(2, resultado.size());
        assertEquals("Electrónica", resultado.get(0).getNombre());
        assertEquals("Ropa", resultado.get(1).getNombre());
        verify(categoriaRepo, times(1)).findAll();
    }

    @Test
    void testCrearCategoriaNueva() {
        CategoriaDTO dto = CategoriaDTO.builder()
                .nombre("Hogar")
                .descripcion("Muebles y más")
                .build();

        Categoria entidadGuardada = Categoria.builder()
                .id(5L).nombre("Hogar").descripcion("Muebles y más")
                .build();

        when(categoriaRepo.findByNombreIgnoreCase("Hogar")).thenReturn(Optional.empty());
        when(categoriaRepo.save(any(Categoria.class))).thenReturn(entidadGuardada);

        CategoriaDTO resultado = categoriaService.crear(dto);

        assertNotNull(resultado.getId());
        assertEquals("Hogar", resultado.getNombre());
        verify(categoriaRepo, times(1)).findByNombreIgnoreCase("Hogar");
        verify(categoriaRepo, times(1)).save(any(Categoria.class));
    }

    @Test
    void testCrearCategoriaDuplicada() {
        CategoriaDTO dto = CategoriaDTO.builder()
                .nombre("Electrónica")
                .descripcion("Duplicada")
                .build();

        Categoria existente = Categoria.builder()
                .id(1L).nombre("Electrónica").descripcion("Dispositivos")
                .build();

        when(categoriaRepo.findByNombreIgnoreCase("Electrónica")).thenReturn(Optional.of(existente));

        assertThrows(ReglaNegocioException.class, () -> categoriaService.crear(dto));
        verify(categoriaRepo, never()).save(any());
    }
}
