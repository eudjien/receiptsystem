package ru.clevertec.checksystem.webmvcjdbc.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.clevertec.checksystem.webmvcjdbc.handler.AnonymousHandlerInterceptor;
import ru.clevertec.checksystem.webmvcjdbc.handler.AuthenticatedHandlerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticatedHandlerInterceptor());
        registry.addInterceptor(new AnonymousHandlerInterceptor()).addPathPatterns("/authentication/login/**");
    }
}
