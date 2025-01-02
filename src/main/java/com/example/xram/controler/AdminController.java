package com.example.xram.controler;

import com.example.xram.model.Admin;
import com.example.xram.service.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

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

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("ADMIN_ID") == null) {
            return "redirect:/admin/login";
        }
        return "admin/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}
