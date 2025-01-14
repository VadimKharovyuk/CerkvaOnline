package com.example.xram.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "church_services")
@Data
public class ChurchService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Название службы (Литургия, Вечерня, Молебен и т.д.)

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private String priestName; // Имя священника

    @Column
    private String description; // Дополнительное описание службы

    private boolean isActive = true;
}