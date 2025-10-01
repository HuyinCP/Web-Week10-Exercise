package com.example.springsecuritydemo.dto;

import java.util.Collection;

public class JwtResponse {
    
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private String fullName;
    private Collection<String> roles;
    private Long expirationTime;
    
    // Default constructor
    public JwtResponse() {}
    
    // Constructor with parameters
    public JwtResponse(String token, String username, String email, String fullName, 
                      Collection<String> roles, Long expirationTime) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
        this.expirationTime = expirationTime;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public Collection<String> getRoles() {
        return roles;
    }
    
    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }
    
    public Long getExpirationTime() {
        return expirationTime;
    }
    
    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }
    
    @Override
    public String toString() {
        return "JwtResponse{" +
                "token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", roles=" + roles +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
