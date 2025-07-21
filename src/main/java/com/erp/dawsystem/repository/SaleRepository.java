package com.erp.dawsystem.repository;

import com.erp.dawsystem.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByClientId(Long clientId);

    List<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Sale> findByClientNameContainingIgnoreCase(String name);
}