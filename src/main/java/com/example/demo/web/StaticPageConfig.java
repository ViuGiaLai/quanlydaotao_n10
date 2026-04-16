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

        // Trang chủ
        registry.addRedirectViewController("/", "/adminlte/index.html");

        // Route đẹp
        registry.addRedirectViewController("/exam-papers", "/adminlte/widgets/exam-papers.html");
        registry.addRedirectViewController("/exam-types", "/adminlte/widgets/exam-types.html");
        registry.addRedirectViewController("/students", "/adminlte/widgets/students.html");
    }
}