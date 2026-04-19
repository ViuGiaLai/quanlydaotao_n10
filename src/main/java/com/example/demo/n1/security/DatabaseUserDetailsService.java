package com.example.demo.n1.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.n1.model.entity.Role;
import com.example.demo.n1.model.entity.User;
import com.example.demo.n1.repository.UserRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DatabaseUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String rawLogin) throws UsernameNotFoundException {
        if (rawLogin == null || rawLogin.isBlank()) {
            throw new UsernameNotFoundException("empty login");
        }
        String login = rawLogin.trim();
        User user = userRepository.findFirstByUsernameIgnoreCaseAndDeletedAtIsNull(login)
                .or(() -> userRepository.findFirstByEmailIgnoreCaseAndDeletedAtIsNull(login))
                .orElseThrow(() -> new UsernameNotFoundException("user not found: " + login));

        boolean active = user.getIsActive() == null || Boolean.TRUE.equals(user.getIsActive());
        String password = user.getPassword() != null ? user.getPassword() : "";

        var authorities = user.getRoles().stream()
                .filter(r -> r.getDeletedAt() == null)
                .filter(r -> r.getIsActive() == null || Boolean.TRUE.equals(r.getIsActive()))
                .map(Role::getCode)
                .filter(code -> code != null && !code.isBlank())
                .map(code -> new SimpleGrantedAuthority("ROLE_" + code.trim()))
                .collect(Collectors.toSet());

        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername() != null ? user.getUsername() : user.getEmail())
                .password(password)
                .disabled(!active)
                .authorities(authorities)
                .build();
    }
}
