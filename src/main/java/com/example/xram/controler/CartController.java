package com.example.xram.controler;

import com.example.xram.model.Cart;
import com.example.xram.model.Product;
import com.example.xram.service.CartService;
import com.example.xram.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cart", cartService.getCart());
        return "cart/cart";
    }

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        if (product != null) {
            cartService.addToCart(product);
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }

    @PostMapping("/update/{productId}")
    public String updateQuantity(@PathVariable Long productId,
                                 @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
}