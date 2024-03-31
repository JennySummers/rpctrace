package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author sxp
 * @create 2019-02-10 23:56
 **/
@Configuration
public class ViewConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/loginPage").setViewName("login");
        registry.addViewController("/" +
                "").setViewName("index");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/test").setViewName("test");
        registry.addViewController("/user").setViewName("user");
        registry.addViewController("/page").setViewName("page");
        registry.addViewController("/custom").setViewName("custom");
        registry.addViewController("/componentPage").setViewName("component");
        registry.addViewController("/index").setViewName("index");

    }
}
