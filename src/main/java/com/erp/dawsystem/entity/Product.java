package com.erp.dawsystem.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Producto")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = true)
    private String description;
}
