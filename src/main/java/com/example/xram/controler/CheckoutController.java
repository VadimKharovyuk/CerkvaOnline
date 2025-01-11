package com.example.xram.controler;

import com.example.xram.dto.OrderRequest;
import com.example.xram.model.Order;
import com.example.xram.service.CartService;
import com.example.xram.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping
    public String showCheckoutForm(Model model) {
        if (cartService.getCart().getItems().isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("orderRequest", new OrderRequest());
        model.addAttribute("cart", cartService.getCart());
        return "checkout/form";
    }

    @PostMapping
    public String processOrder(@ModelAttribute OrderRequest orderRequest, Model model) {
        try {
            Order order = orderService.createOrder(orderRequest);
            return "redirect:/checkout/success?orderId=" + order.getId();
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            return "checkout/form";
        }
    }

    @GetMapping("/success")
    public String showSuccessPage(@RequestParam Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "checkout/success";
    }
}