package com.erp.dawsystem.service.impl;

import com.erp.dawsystem.entity.Sale;
import com.erp.dawsystem.repository.SaleRepository;
import com.erp.dawsystem.service.interfaces.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Override
    public Sale findById(Long id) {
        Optional<Sale> optional = saleRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void save(Sale sale) {
        saleRepository.save(sale);
    }

    @Override
    public void update(Sale sale) {
        if (sale.getId() != null && saleRepository.existsById(sale.getId())) {
            saleRepository.save(sale);
        }
    }

    @Override
    public void delete(Long id) {
        saleRepository.deleteById(id);
    }

    @Override
    public List<Sale> findByClientId(Long clientId) {
        return saleRepository.findByClientId(clientId);
    }

    @Override
    public List<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        return saleRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public List<Sale> searchByClientName(String name) {
        return saleRepository.findByClientNameContainingIgnoreCase(name);
    }


    @Override
    public double getTotalVentasHoy() {
        LocalDate today = LocalDate.now();
        Double total = saleRepository.getTotalSalesByDate(today);
        return total != null ? total : 0.0;
    }

    @Override
    public double getTotalVentasMes() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        Double total = saleRepository.getTotalSalesBetweenDates(startOfMonth, endOfMonth);
        return total != null ? total : 0.0;
    }
















}
