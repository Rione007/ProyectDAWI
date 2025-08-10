package com.erp.dawsystem.service.impl;

import com.erp.dawsystem.entity.Category;
import com.erp.dawsystem.entity.Product;
import com.erp.dawsystem.repository.ProductRepository;
import com.erp.dawsystem.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void update(Product product) {
        if (product.getId() != null && productRepository.existsById(product.getId())) {
            productRepository.save(product);
        }
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findByCategory(Category category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }

    @Override
    public Page<Product> searchByName(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<Product> findByStockLessThanEqual(int stock , Pageable pageable) {
        return productRepository.findByStockLessThanEqual(stock ,pageable );
    }

    @Override
    public List<Product> findByPriceBetween(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetween(min, max);
    }

    @Override
    public Page<Product> findByNameContainingAndCategory(String name, Category category, Pageable pageable) {
        return productRepository.findByNameContainingAndCategory(name, category, pageable);
    }
    @Override
    public int countProductosStockBajo() {
        return (int) findByStockLessThanEqual(14, Pageable.unpaged()).getTotalElements();
    }



}
