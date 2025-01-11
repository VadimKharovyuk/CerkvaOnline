package com.example.xram.service;

import com.example.xram.model.Cart;
import com.example.xram.model.CartItem;
import com.example.xram.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {

    private final Cart cart;

    public void addToCart(Product product) {
        CartItem cartItem = cart.getItems().get(product.getId());
        if (cartItem == null) {
            cartItem = new CartItem(product, 1, product.getPrice());
            cart.getItems().put(product.getId(), cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
        recalculateCartTotal();
    }

    public void removeFromCart(Long productId) {
        cart.getItems().remove(productId);
        recalculateCartTotal();
    }

    public void updateQuantity(Long productId, int quantity) {
        CartItem cartItem = cart.getItems().get(productId);
        if (cartItem != null && quantity > 0) {
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice(cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(quantity)));
            recalculateCartTotal();
        }
    }

    public void clearCart() {
        cart.getItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
    }

    private void recalculateCartTotal() {
        BigDecimal total = cart.getItems().values().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(total);
    }

    public Cart getCart() {
        return cart;
    }
}