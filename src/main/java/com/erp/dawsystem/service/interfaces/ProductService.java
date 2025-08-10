package com.erp.dawsystem.service.interfaces;

import com.erp.dawsystem.entity.Category;
import com.erp.dawsystem.entity.Product;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService{

    Page<Product> findAll(Pageable pageable);
    //Listar todos los productos
    Product findById(Long id); //Obtener uno por ID
    void save(Product product); //Crear uno nuevo
    void update(Product product); //Actualizar datos de un producto
    void delete(Long id); //Eliminar

    int countProductosStockBajo();



    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> searchByName(String name, Pageable pageable);
    Page<Product> findByStockLessThanEqual(int stock,Pageable pageable ); //Productos con bajo stock
    Page<Product> findByNameContainingAndCategory(String name, Category category, Pageable pageable);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max); //Buscar productos por rango de precio

}
