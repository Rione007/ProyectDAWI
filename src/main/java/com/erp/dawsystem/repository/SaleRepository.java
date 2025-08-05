package com.erp.dawsystem.repository;

import com.erp.dawsystem.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByClientId(Long clientId);

    List<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Sale> findByClientNameContainingIgnoreCase(String name);

    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.date = :date")
    Double getTotalSalesByDate(@Param("date") LocalDate date);



    @Query("SELECT SUM(s.total) FROM Sale s WHERE s.date BETWEEN :startDate AND :endDate")
    Double getTotalSalesBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


}