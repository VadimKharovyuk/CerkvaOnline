package com.example.xram.controler;

import com.example.xram.dto.ChurchServiceDto;
import com.example.xram.model.ChurchService;
import com.example.xram.service.ChurchServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/church-services")
@RequiredArgsConstructor
public class AdminChurchServiceController {

    private final ChurchServiceService churchServiceService;

    @GetMapping
    public String listServices(Model model) {
        model.addAttribute("services", churchServiceService.getUpcomingServices());
        return "admin/church-services/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("serviceDto", new ChurchServiceDto());
        return "admin/church-services/form";
    }

    @PostMapping
    public String createService(@ModelAttribute ChurchServiceDto serviceDto,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "admin/church-services/form";
        }

        try {
            churchServiceService.createService(serviceDto);
            redirectAttributes.addFlashAttribute("success", "Служба успешно создана");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/admin/church-services";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ChurchService service = churchServiceService.getServiceById(id);
        model.addAttribute("serviceDto", service);
        return "admin/church-services/form";
    }

    @PostMapping("/edit/{id}")
    public String updateService(@PathVariable Long id,
                                @ModelAttribute ChurchServiceDto serviceDto,
                                RedirectAttributes redirectAttributes) {
        try {
            churchServiceService.updateService(id, serviceDto);
            redirectAttributes.addFlashAttribute("success", "Служба успешно обновлена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/church-services";
    }

    @PostMapping("/delete/{id}")
    public String deleteService(@PathVariable Long id,
                                RedirectAttributes redirectAttributes) {
        try {
            churchServiceService.deleteService(id);
            redirectAttributes.addFlashAttribute("success", "Служба успешно удалена");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/church-services";
    }
}