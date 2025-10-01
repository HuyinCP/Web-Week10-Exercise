package com.example.springsecuritydemo.controller;

import com.example.springsecuritydemo.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    
    @GetMapping("/")
    public String userHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("fullName", userDetails.getFullName());
        
        return "user/user-home";
    }
    
    @GetMapping("/profile")
    public String userProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        model.addAttribute("user", userDetails.getUser());
        model.addAttribute("fullName", userDetails.getFullName());
        
        return "user/user-profile";
    }
}
