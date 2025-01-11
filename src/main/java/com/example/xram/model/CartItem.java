package com.example.xram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private Product product;
    private int quantity;
    private BigDecimal totalPrice;
}