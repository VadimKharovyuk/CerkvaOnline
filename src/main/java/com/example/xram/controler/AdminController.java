package com.example.xram.controler;

import com.example.xram.model.Admin;
import com.example.xram.model.ContactRequest;
import com.example.xram.service.AdminService;
import com.example.xram.service.ContactService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final ContactService contactService;

    @GetMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            Admin admin = adminService.authenticate(username, password);
            session.setAttribute("ADMIN_ID", admin.getId());
            session.setAttribute("ADMIN_NAME", admin.getUsername());
            return "redirect:/admin/dashboard";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
            return "admin/login";
        }
    }

    // Для первичной регистрации админа
    @GetMapping("/xpam")
    public String initialSetup() {
        // Проверяем, есть ли уже хоть один админ в системе
        if (adminService.hasAnyAdmin()) {
            return "redirect:/admin/login";
        }
        return "admin/register";
    }


    @GetMapping("/register")
    public String showRegisterForm(HttpSession session) {
        if (session.getAttribute("ADMIN_ID") != null) {
            return "admin/register";
        }
        return "redirect:/admin/login";
    }

    @PostMapping("/register")
    public String registerAdmin(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String confirmPassword,
                                Model model) {
        try {
            if (!password.equals(confirmPassword)) {
                model.addAttribute("error", "Пароли не совпадают");
                return "admin/register";
            }

            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);

            adminService.registerAdmin(admin);
            return "redirect:/admin/login";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при регистрации: " + e.getMessage());
            return "admin/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("ADMIN_ID") == null) {
            return "redirect:/admin/login";
        }

        return "admin/dashboard";
    }

    @GetMapping("/contacts")
    public String showAllContacts(Model model) {
        List<ContactRequest> contacts = contactService.findAll();
        model.addAttribute("contacts", contacts);
        return "admin/contacts";
    }
}
