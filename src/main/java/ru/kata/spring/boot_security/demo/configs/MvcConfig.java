package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**")
//                .addResourceLocations("resources/templates/");
////        registry.addResourceHandler("/**")
////                .addResourceLocations("resources/static/");
//    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/api/admin").setViewName("admin_fetch.html");
        registry.addViewController("/api/user").setViewName("user_fetch.html");
    }
}

