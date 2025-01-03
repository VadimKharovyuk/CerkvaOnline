package com.example.xram.repository;

import com.example.xram.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByServiceIdAndActiveOrderByCreatedAtDesc(Long serviceId, boolean active);
}
