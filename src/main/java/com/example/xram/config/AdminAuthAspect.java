package com.example.xram.config;

import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



@Aspect
@Component
public class AdminAuthAspect {
    @Around("@annotation(RequireAdmin)")
    public Object checkAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getSession();

        if (session.getAttribute("ADMIN_ID") == null) {
            throw new RuntimeException("Admin authentication required");
        }

        return joinPoint.proceed();
    }
}
