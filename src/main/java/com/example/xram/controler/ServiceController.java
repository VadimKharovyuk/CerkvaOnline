package com.example.xram.controler;

import com.example.xram.service.EServiceService;
import com.example.xram.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ServiceController {
    private final EServiceService serviceService;
    private final ProductService productService;


    @GetMapping("/{serviceId}/products")
    public String getProductsForService(@PathVariable Long serviceId, Model model) {
        model.addAttribute("service", serviceService.getById(serviceId));
        model.addAttribute("products", productService.getProductsByService(serviceId));
        return "admin/products/list";
    }


    @GetMapping("/services")
    public String showServices(Model model) {
        model.addAttribute("services", serviceService.getAll());
        return "services/list"; // список услуг для пользователей
    }

    @GetMapping("/services/{id}")
    public String getServiceDetails(@PathVariable Long id, Model model) {
        model.addAttribute("service", serviceService.getById(id));
        return "services/details";
    }

}
