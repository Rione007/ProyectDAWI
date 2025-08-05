package com.erp.dawsystem.service.impl;

import com.erp.dawsystem.entity.SaleDetail;
import com.erp.dawsystem.repository.SaleDetailRepository;
import com.erp.dawsystem.service.interfaces.SaleDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleDetailServiceImpl implements SaleDetailService {
    @Autowired
    private SaleDetailRepository saleDetailRepository;

    @Override
    public List<SaleDetail> findAll() {
        return saleDetailRepository.findAll();
    }

    @Override
    public Optional<SaleDetail> findById(Long id) {
        return saleDetailRepository.findById(id);
    }

    @Override
    public SaleDetail save(SaleDetail saleDetail) {
        return saleDetailRepository.save(saleDetail);
    }

    @Override
    public void deleteById(Long id) {
        saleDetailRepository.deleteById(id);
    }

    @Override
    public List<SaleDetail> findBySaleId(Long saleId) {
        return saleDetailRepository.findBySaleId(saleId);
    }

    @Override
    public List<SaleDetail> findByProductId(Long productId) {
        return saleDetailRepository.findByProductId(productId);
    }
}
