package com.example.xram.repository;

import com.example.xram.model.EService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EServiceRepository extends JpaRepository<EService, Long> {
    List<EService> findAllByActiveOrderByOrderIndexAsc(boolean active);
    Optional<EService> findTopByOrderByOrderIndexDesc();
}
