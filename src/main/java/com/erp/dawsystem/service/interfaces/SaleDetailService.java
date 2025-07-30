package com.erp.dawsystem.service.interfaces;

import com.erp.dawsystem.entity.SaleDetail;

import java.util.List;
import java.util.Optional;

public interface SaleDetailService {
    List<SaleDetail> findAll();

    // Obtener por ID
    Optional<SaleDetail> findById(Long id);

    // Crear o guardar
    SaleDetail save(SaleDetail saleDetail);

    // Eliminar por ID
    void deleteById(Long id);

    // Filtrar por ID de Venta
    List<SaleDetail> findBySaleId(Long saleId);

    // Filtrar por ID de Producto
    List<SaleDetail> findByProductId(Long productId);
}
