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

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
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
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Product> findByStockLessThanEqual(int stock) {
        return productRepository.findByStockLessThanEqual(stock);
    }

    @Override
    public void adjustStock(Long id, int stock) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        product.setStock(stock);
        productRepository.save(product);
    }

    @Override
    public List<Product> findByPriceBetween(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Product> findByNameContainingAndCategory(String name, Category category) {
        return productRepository.findByNameContainingAndCategory(name, category);
    }


}
