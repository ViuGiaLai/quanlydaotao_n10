package com.example.demo.n1.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Mật khẩu mới luôn mã hóa BCrypt; khi đăng nhập chấp nhận cả hash BCrypt trong DB
 * hoặc chuỗi thuần (legacy) để không khóa tài khoản đã tạo trước khi có Security.
 */
public class LegacyCompatiblePasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        return bcrypt.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null || rawPassword == null) {
            return false;
        }
        String enc = encodedPassword.trim();
        if (enc.startsWith("$2a$") || enc.startsWith("$2b$") || enc.startsWith("$2y$")) {
            return bcrypt.matches(rawPassword, enc);
        }
        return enc.contentEquals(rawPassword);
    }
}
