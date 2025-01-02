package com.example.xram.repository;

import com.example.xram.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
   Optional<Admin> findByUsername(String username);

   boolean existsByUsername(String username);

}
