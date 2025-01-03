package com.example.xram.controler;

import com.example.xram.model.EService;
import com.example.xram.service.EServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/admin/services")
@RequiredArgsConstructor
public class AdminServiceController {
    private final EServiceService serviceService;

    // Показать список всех услуг
    @GetMapping
    public String getAllServices(Model model) {
        model.addAttribute("services", serviceService.getAll());
        return "admin/services/list";
    }

    // Показать форму создания услуги
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("service", new EService());
        return "admin/services/create";
    }

    // Обработка создания новой услуги
    @PostMapping
    public String createService(@ModelAttribute EService service,
                                @RequestParam("image") MultipartFile imageFile,
                                RedirectAttributes redirectAttributes) {
        try {
            serviceService.saveWithImage(service, imageFile);
            redirectAttributes.addFlashAttribute("success", "Услуга успешно создана");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при загрузке изображения");
            return "redirect:/admin/services/new";
        }
        return "redirect:/admin/services";
    }

    // Показать детали услуги
    @GetMapping("/{id}")
    public String getService(@PathVariable Long id, Model model) {
        model.addAttribute("service", serviceService.getById(id));
        return "admin/services/details";
    }

    // Показать форму редактирования
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("service", serviceService.getById(id));
        return "admin/services/edit";
    }

    // Обработка изображения
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getServiceImage(@PathVariable Long id) {
        byte[] imageData = serviceService.getImageById(id);
        String imageType = serviceService.getImageType(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageType))
                .body(imageData);
    }

    // Удаление услуги
    @PostMapping("/{id}/delete")
    public String deleteService(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        serviceService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Услуга успешно удалена");
        return "redirect:/admin/services";
    }
}