package com.example.xram.service;

import com.example.xram.model.Admin;
import com.example.xram.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public Admin authenticate(String username, String password) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!admin.getPassword().equals(password)) { // В реальном проекте используйте хеширование паролей
            throw new RuntimeException("Invalid password");
        }

        return admin;
    }
}
