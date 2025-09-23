package com.supernova.inventario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class PingMovimientosController {
    @GetMapping("/api/movimientos/ping")
    public Map<String, String> ping() {
        return Map.of("status", "ok");
    }
}

