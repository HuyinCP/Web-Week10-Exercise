package com.example.springsecuritydemo.controller;

import com.example.springsecuritydemo.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("fullName", userDetails.getFullName());
        model.addAttribute("roles", userDetails.getAuthorities());
        
        return "dashboard";
    }
}
