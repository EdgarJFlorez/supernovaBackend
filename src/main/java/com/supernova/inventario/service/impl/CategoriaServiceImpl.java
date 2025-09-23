package com.supernova.inventario.service.impl;

import com.supernova.inventario.dto.CategoriaDTO;
import com.supernova.inventario.exception.ReglaNegocioException;
import com.supernova.inventario.model.Categoria;
import com.supernova.inventario.repository.CategoriaRepository;
import com.supernova.inventario.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repo;

    @Override
    public List<CategoriaDTO> listar() {
        return repo.findAll().stream()
                .map(c -> new CategoriaDTO(c.getId(), c.getNombre(), c.getDescripcion()))
                .toList();
    }

    @Override
    public CategoriaDTO crear(CategoriaDTO dto) {
        // ✅ Validación de unicidad antes de guardar
        repo.findByNombreIgnoreCase(dto.getNombre())
                .ifPresent(c -> { throw new ReglaNegocioException("La categoría ya existe: " + dto.getNombre()); });

        Categoria c = Categoria.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();

        c = repo.save(c);
        dto.setId(c.getId());
        return dto;
    }
}


