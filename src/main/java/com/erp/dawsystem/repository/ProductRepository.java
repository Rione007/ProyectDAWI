package com.erp.dawsystem.repository;

import com.erp.dawsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByStockLessThanEqual(int stock);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);
}

