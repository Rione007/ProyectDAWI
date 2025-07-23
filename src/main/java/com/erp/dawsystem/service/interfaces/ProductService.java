package com.erp.dawsystem.service.interfaces;

import com.erp.dawsystem.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    List<Product> findAll(); //Listar todos los productos
    Product findById(Long id); //Obtener uno por ID
    void save(Product product); //Crear uno nuevo
    void update(Product product); //Actualizar datos de un producto
    void delete(Long id); //Eliminar


    List<Product> findByCategory(String category); //Filtrar por categoria
    List<Product> searchByName(String name); //Búsqueda por nombre(contiene)
    List<Product> findByStockLessThanEqual(int stock); //Productos con bajo stock
    void adjustStock(Long id, int stock); //Aumentar o disminuir el stock

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max); //Buscar productos por rango de precio

}
