package com.erp.dawsystem.repository;

import com.erp.dawsystem.entity.Product;
import com.erp.dawsystem.entity.Sale;
import com.erp.dawsystem.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, Long> {

    List<SaleDetail> findBySale(Sale sale);

    List<SaleDetail> findByProduct(Product product);
    List<SaleDetail> findBySaleId(Long saleId);
    List<SaleDetail> findByProductId(Long productId);

}
