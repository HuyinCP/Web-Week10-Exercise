package com.example.springsecuritydemo.service;

import com.example.springsecuritydemo.dto.JwtRequest;
import com.example.springsecuritydemo.dto.JwtResponse;
import com.example.springsecuritydemo.security.CustomUserDetails;
import com.example.springsecuritydemo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class JwtService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public JwtResponse authenticate(JwtRequest jwtRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    jwtRequest.getUsername(),
                    jwtRequest.getPassword()
                )
            );
            
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
            
            String token = jwtUtil.generateToken(userDetails);
            
            return new JwtResponse(
                token,
                customUserDetails.getUsername(),
                customUserDetails.getEmail(),
                customUserDetails.getFullName(),
                customUserDetails.getAuthorities().stream()
                    .map(auth -> auth.getAuthority())
                    .collect(Collectors.toList()),
                jwtUtil.getExpirationTime()
            );
            
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public String getUsernameFromToken(String token) {
        return jwtUtil.getUsernameFromToken(token);
    }
}
