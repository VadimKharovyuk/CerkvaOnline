package com.example.xram.controler;

import com.example.xram.service.EServiceService;
import com.example.xram.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final EServiceService serviceService;
    @GetMapping("/{serviceId}/products")
    public String getProductsForService(@PathVariable Long serviceId, Model model) {
        model.addAttribute("service", serviceService.getById(serviceId));
        model.addAttribute("products", productService.getProductsByService(serviceId));
        return "admin/products/list";  // Изменен путь к представлению
    }
}
