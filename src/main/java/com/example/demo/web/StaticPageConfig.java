package com.example.demo.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticPageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // Giữ adminlte chính
        registry.addResourceHandler("/adminlte/**")
                .addResourceLocations("classpath:/static/adminlte/");

        // 🔥 FIX ../css → /css
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/adminlte/css/");

        // 🔥 FIX ../js → /js
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/adminlte/js/");

        // 🔥 FIX ../assets → /assets
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/adminlte/assets/");

        // (Nếu AdminLTE có plugins thì thêm cái này cho chắc)
        registry.addResourceHandler("/plugins/**")
                .addResourceLocations("classpath:/static/adminlte/plugins/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // Trang chủ → cổng /admin (yêu cầu đăng nhập, xem SecurityConfig)
        registry.addRedirectViewController("/", "/admin");
        registry.addRedirectViewController("/admin", "/admin/exams");

        // Giữ tương thích URL cũ (AdminLTE static đã được thay bằng Thymeleaf templates)
        registry.addRedirectViewController("/adminlte/index.html", "/admin/exams");

        // Admin pages (Thymeleaf views dưới src/main/resources/templates/admin/)
        registry.addViewController("/admin/exams").setViewName("admin/exams");
        registry.addViewController("/admin/exam-papers").setViewName("admin/exam-papers");
        registry.addViewController("/admin/exam-types").setViewName("admin/exam-types");
        registry.addViewController("/admin/exam-rooms").setViewName("admin/exam-rooms");
        registry.addViewController("/admin/students").setViewName("admin/students");
        registry.addViewController("/admin/roles").setViewName("admin/roles");
        registry.addViewController("/admin/users").setViewName("admin/users");
        registry.addViewController("/admin/permissions").setViewName("admin/permissions");
        registry.addViewController("/admin/role-permissions").setViewName("admin/role-permissions");
        registry.addViewController("/admin/user-roles").setViewName("admin/user-roles");
    }
}