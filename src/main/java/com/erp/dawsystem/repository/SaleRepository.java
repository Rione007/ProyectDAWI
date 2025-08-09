package com.erp.dawsystem.repository;

import com.erp.dawsystem.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    // 🔍 Buscar todas las ventas por ID del cliente (sin paginación)
    List<Sale> findByClientId(Long clientId);



    // 🔍 Buscar ventas dentro de un rango de fechas con paginación
    Page<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // 🔍 Buscar ventas por nombre de cliente con paginación
    Page<Sale> findByClientNameContainingIgnoreCase(String name, Pageable pageable);

    // 🔍 Buscar todas las ventas con paginación
    Page<Sale> findAll(Pageable pageable);

    // 📊 Obtener el total de ventas realizadas en una fecha específica
    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.date = :date")
    Double getTotalSalesByDate(@Param("date") LocalDate date);

    // 📊 Obtener el total de ventas realizadas entre dos fechas
    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.date BETWEEN :startDate AND :endDate")
    Double getTotalSalesBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
