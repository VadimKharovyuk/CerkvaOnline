package com.example.xram.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(name = "image_data")
    private byte[] imageData;
    private String imageType;

    @Column(nullable = false)
    private BigDecimal price;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private EService service;

    private LocalDateTime createdAt = LocalDateTime.now();
    private boolean active = true;
}
