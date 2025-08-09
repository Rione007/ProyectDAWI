package com.erp.dawsystem.service.interfaces;

import com.erp.dawsystem.entity.Sale;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleService {
    List<Sale> findAll();
    Sale findById(Long id);
    void save(Sale sale);
    void update(Sale sale);
    void delete(Long id);
    double getTotalVentasHoy();
    double getTotalVentasMes();

    List<Sale> findByClientId(Long clientId);
    Page<Sale> findAll(Pageable pageable);
    Page<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<Sale> searchByClientName(String name, Pageable pageable);








}
