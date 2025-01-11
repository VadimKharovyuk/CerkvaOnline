package com.example.xram.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String address;
}