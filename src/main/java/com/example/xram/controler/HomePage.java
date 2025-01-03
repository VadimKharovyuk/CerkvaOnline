package com.example.xram.controler;

import com.example.xram.model.ContactRequest;
import com.example.xram.model.Subscriber;
import com.example.xram.service.ContactService;
import com.example.xram.service.EmailService;
import com.example.xram.service.SubscriberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomePage {
    private final ContactService contactService;
    private final EmailService emailService;
    private final SubscriberService subscriberService;

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

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            Subscriber subscriber = new Subscriber();
            subscriber.setEmail(email);
            subscriberService.save(subscriber);
            emailService.sendSubscriptionConfirmation(email);
            redirectAttributes.addFlashAttribute("subscribeSuccess", "Спасибо за подписку! Проверьте почту.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("subscribeError", "Ошибка при подписке. Попробуйте позже.");
        }
        return "redirect:/";
    }
}
