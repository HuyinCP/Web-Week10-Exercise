package com.example.springsecuritydemo.controller;

import com.example.springsecuritydemo.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ApiController {

    @GetMapping("/public")
    public ResponseEntity<?> publicEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is a public endpoint - no authentication required");
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> userEndpoint(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is a user endpoint - requires USER, MODERATOR, or ADMIN role");
        response.put("user", userDetails.getUsername());
        response.put("email", userDetails.getEmail());
        response.put("fullName", userDetails.getFullName());
        response.put("roles", userDetails.getAuthorities());
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> moderatorEndpoint(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is a moderator endpoint - requires MODERATOR or ADMIN role");
        response.put("user", userDetails.getUsername());
        response.put("email", userDetails.getEmail());
        response.put("fullName", userDetails.getFullName());
        response.put("roles", userDetails.getAuthorities());
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminEndpoint(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is an admin endpoint - requires ADMIN role");
        response.put("user", userDetails.getUsername());
        response.put("email", userDetails.getEmail());
        response.put("fullName", userDetails.getFullName());
        response.put("roles", userDetails.getAuthorities());
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Authentication required");
            return ResponseEntity.status(401).body(error);
        }
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        Map<String, Object> response = new HashMap<>();
        response.put("username", userDetails.getUsername());
        response.put("email", userDetails.getEmail());
        response.put("fullName", userDetails.getFullName());
        response.put("roles", userDetails.getAuthorities());
        response.put("enabled", userDetails.isEnabled());
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public ResponseEntity<?> testEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Test endpoint");
        response.put("authenticated", authentication != null && authentication.isAuthenticated());
        
        if (authentication != null && authentication.isAuthenticated()) {
            response.put("principal", authentication.getName());
            response.put("authorities", authentication.getAuthorities());
        }
        
        response.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }
}
