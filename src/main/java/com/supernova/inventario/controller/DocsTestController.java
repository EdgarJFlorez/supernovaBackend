package com.supernova.inventario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class DocsTestController {
    @GetMapping("/api/docstest/ping")
    public Map<String, String> ping() {
        return Map.of("status", "ok");
    }
}
