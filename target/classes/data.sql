-- Categoría de prueba
INSERT INTO categoria (id, nombre, descripcion, created_at, updated_at)
VALUES (901, 'Pruebas', 'Categoría temporal', NOW(), NOW());

-- Producto de prueba
INSERT INTO producto (id, nombre, descripcion, stock, precio, categoria_id, created_at, updated_at)
VALUES (9001, 'Teclado Mecánico', 'Producto demo', 10, 59.99, 901, NOW(), NOW());
