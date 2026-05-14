package com.example.demo.n1.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.n1.model.entity.Role;
import com.example.demo.n1.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String principal = authentication.getName();
        
        Optional<com.example.demo.n1.model.entity.User> userOpt = userRepository
                .findFirstByUsernameIgnoreCaseAndDeletedAtIsNull(principal)
                .or(() -> userRepository.findFirstByEmailIgnoreCaseAndDeletedAtIsNull(principal));
        
        if (userOpt.isPresent()) {
            var user = userOpt.get();
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);
            
            // Check roles to redirect
            Set<Role> roles = user.getRoles();
            
            // Check for LECTURER/TEACHER role
            boolean isLecturer = roles.stream()
                .anyMatch(r -> "LECTURER".equalsIgnoreCase(r.getCode()) 
                           || "TEACHER".equalsIgnoreCase(r.getCode()));
            if (isLecturer) {
                response.sendRedirect("/teacher");
                return;
            }
            
            // Check for STUDENT role
            boolean isStudent = roles.stream()
                .anyMatch(r -> "STUDENT".equalsIgnoreCase(r.getCode()));
            if (isStudent) {
                response.sendRedirect("/student");
                return;
            }
            
            // Check for ADMIN role
            boolean isAdmin = roles.stream()
                .anyMatch(r -> "ADMIN".equalsIgnoreCase(r.getCode()));
            if (isAdmin) {
                response.sendRedirect("/admin");
                return;
            }
        }
        
        // No valid role -> redirect to unauthorized page
        response.sendRedirect("/unauthorized");
    }
}
