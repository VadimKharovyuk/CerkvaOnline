package com.example.xram.controler;

import com.example.xram.model.Product;
import com.example.xram.service.EServiceService;
import com.example.xram.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;
    private final EServiceService serviceService;

    @GetMapping("/{serviceId}/products")
    public String getProductsForService(@PathVariable Long serviceId, Model model) {
        model.addAttribute("service", serviceService.getById(serviceId));
        model.addAttribute("products", productService.getProductsByService(serviceId));
        return "admin/products/list";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("services", serviceService.getAll());
        return "admin/products/create";
    }

    @PostMapping
    public String createProduct(@ModelAttribute Product product,
                                @RequestParam("image") MultipartFile imageFile,
                                RedirectAttributes redirectAttributes) {
        try {
            productService.saveWithImage(product, imageFile);
            redirectAttributes.addFlashAttribute("success", "Товар успешно добавлен");
            return "redirect:/admin/services/" + product.getService().getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании товара");
            return "redirect:/admin/products/new";
        }
    }


}
