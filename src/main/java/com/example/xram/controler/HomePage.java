package com.example.xram.controler;

import com.example.xram.model.ContactRequest;
import com.example.xram.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller

public class HomePage {
        private final ContactService contactService;

        @Autowired
        public HomePage(ContactService contactService) {
            this.contactService = contactService;
        }

        @GetMapping()
        public String homePage(Model model) {
            model.addAttribute("contactRequest", new ContactRequest());
            return "home";
        }
    @PostMapping("/contact")
    public String saveContact(@Valid @ModelAttribute ContactRequest contact,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "home";
        }

        try {
            contactService.save(contact);
            model.addAttribute("success", "Сообщение успешно отправлено");
        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка при отправке");
        }
        return "redirect:/";
    }
}
