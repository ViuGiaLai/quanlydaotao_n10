package com.example.demo.n1.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.n1.model.entity.User;
import com.example.demo.n1.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    public static class UserRequest {
        public String username;
        public String password;
        public String email;
        public String phone;
        public String avatarUrl;
    }

    public static record UserResponse(UUID id, String username, String email, String phone, String avatarUrl, Boolean isActive) {}

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getAvatarUrl(),
                user.getIsActive());
    }

    @PostMapping
    public UserResponse create(@RequestBody UserRequest request) {
        User user = userService.createUser(request.username, request.password, request.email, request.phone, request.avatarUrl);
        return toResponse(user);
    }

    @PostMapping("/{userId}/roles/{roleId}")
    public UserResponse assignRole(@PathVariable UUID userId,
                                   @PathVariable UUID roleId) {
        User user = userService.assignRole(userId, roleId);
        return toResponse(user);
    }

    @GetMapping
    public List<UserResponse> list() {
        return userService.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/list")
    public List<UserResponse> listAll() {
        return list();
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable UUID id, @RequestBody UserRequest request) {
        User user = userService.updateUser(id, request.username, request.password, request.email, request.phone, request.avatarUrl);
        return toResponse(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/delete")
    public void deleteLegacy(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    @PostMapping("/import")
    public List<UserResponse> importExcel(@RequestBody List<UserRequest> users) {
        return users.stream()
                .map(request -> userService.saveUser(createFromRequest(request)))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private User createFromRequest(UserRequest request) {
        User user = new User();
        user.setUsername(request.username);
        user.setPassword(request.password);
        user.setEmail(request.email);
        user.setPhone(request.phone);
        user.setAvatarUrl(request.avatarUrl);
        user.setIsActive(true);
        return user;
    }
}