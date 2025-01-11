package com.example.xram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Long, CartItem> items = new HashMap<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}