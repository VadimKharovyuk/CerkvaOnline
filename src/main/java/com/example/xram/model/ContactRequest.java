package com.example.xram.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ContactRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Имя обязательно для заполнения")
    private String name;

    @Column(nullable = false)
    @Email(message = "Введите корректный email")
    @NotBlank(message = "Email обязателен для заполнения")
    private String email;

    @Column(name = "phone_number", nullable = false)
    @NotBlank(message = "Телефон обязателен для заполнения")
    private String phoneNumber;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Сообщение обязательно для заполнения")
    private String message;


    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
