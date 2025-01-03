package com.example.xram.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Email(message = "Некорректный email адрес")
    private String email;

    @Column(name = "subscribed_at")
    private LocalDateTime subscribedAt = LocalDateTime.now();

    @Column(name = "is_active")
    private boolean active = true;
}
