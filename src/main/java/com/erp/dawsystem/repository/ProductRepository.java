package com.erp.dawsystem.repository;

import com.erp.dawsystem.entity.Category;
import com.erp.dawsystem.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);

    Page<Product> findByCategory(Category category, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByStockLessThanEqual(int stock ,Pageable pageable);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    Page<Product> findByNameContainingAndCategory(String name, Category category, Pageable pageable);


}

