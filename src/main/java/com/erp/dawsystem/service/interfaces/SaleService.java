package com.erp.dawsystem.service.interfaces;

import com.erp.dawsystem.entity.Sale;

import java.time.LocalDate;
import java.util.List;


public interface SaleService {
    List<Sale> findAll();
    Sale findById(Long id);
    void save(Sale sale);
    void update(Sale sale);
    void delete(Long id);
    double getTotalVentasHoy();
    double getTotalVentasMes();

    List<Sale> findByClientId(Long clientId);
    List<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Sale> searchByClientName(String name);









}
