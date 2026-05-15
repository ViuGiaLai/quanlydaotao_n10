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

        // 🔥 FIX ../js → /js (bao gồm cả adminlte và custom JS)
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/adminlte/js/", "classpath:/static/js/");

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
        
        // Logout - redirect to login with logout param
        registry.addRedirectViewController("/logout", "/login?logout");

        // Giữ tương thích URL cũ (AdminLTE static đã được thay bằng Thymeleaf templates)
        registry.addRedirectViewController("/adminlte/index.html", "/admin/exams");

        // Admin pages (Thymeleaf views dưới src/main/resources/templates/admin/)
        registry.addViewController("/admin/exams").setViewName("admin/exams");
        registry.addViewController("/admin/exam-papers").setViewName("admin/exam-papers");
        registry.addViewController("/admin/exam-types").setViewName("admin/exam-types");
        registry.addViewController("/admin/exam-rooms").setViewName("admin/exam-rooms");
        registry.addViewController("/admin/exam-room-detail").setViewName("admin/exam-room-detail");
        registry.addViewController("/admin/exam-registrations").setViewName("admin/exam-registration");
        registry.addViewController("/admin/exam-results").setViewName("admin/exam-result");
        registry.addViewController("/admin/course-class-subjects").setViewName("admin/course-class-subjects");
        registry.addViewController("/admin/semesters").setViewName("admin/semesters");
        registry.addViewController("/admin/subjects").setViewName("admin/subjects");
        registry.addViewController("/admin/course-classes").setViewName("admin/course-classes");
        registry.addViewController("/admin/students").setViewName("admin/students");
        registry.addViewController("/admin/roles").setViewName("admin/roles");
        registry.addViewController("/admin/users").setViewName("admin/users");
        registry.addViewController("/admin/permissions").setViewName("admin/permissions");
        registry.addViewController("/admin/role-permissions").setViewName("admin/role-permissions");
        registry.addViewController("/admin/user-roles").setViewName("admin/user-roles");
        
        // Quản lý Giảng viên
        registry.addViewController("/admin/teachers").setViewName("admin/teachers");
        
        // Quản lý Lớp Sinh viên
        registry.addViewController("/admin/student-classes").setViewName("admin/student-classes");
        registry.addViewController("/admin/student-classe-sections").setViewName("admin/student-classe-sections");
        registry.addViewController("/admin/advisor-classe-sections").setViewName("admin/advisor-classe-sections");
        
        // Quản lý Danh mục
        registry.addViewController("/admin/academic-years").setViewName("admin/academic-years");
        registry.addViewController("/admin/departments").setViewName("admin/departments");
        registry.addViewController("/admin/majors").setViewName("admin/majors");
        registry.addViewController("/admin/training-programs").setViewName("admin/training-programs");
    }
}