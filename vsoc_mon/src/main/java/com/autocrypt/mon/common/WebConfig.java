package com.autocrypt.mon.common;

import com.autocrypt.mon.filter.HttpLoggingFilter;
import com.autocrypt.mon.interceptor.ApiLoggingInterceptor;
import com.autocrypt.mon.interceptor.SessionCheckInterceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final SessionCheckInterceptor sessionCheckInterceptor;
    private final ApiLoggingInterceptor apiLoggingInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/*").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(sessionCheckInterceptor)
                           .addPathPatterns("/session-check");

        interceptorRegistry.addInterceptor(apiLoggingInterceptor)
                           .addPathPatterns("/**");
    }


    @Bean
    public FilterRegistrationBean getFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new HttpLoggingFilter());

        //TODO: formData 형식 RequestWrapper 안됨
        registrationBean.addUrlPatterns("/loginSuccess", "/error/login", "/logoutSuccess");
        return registrationBean;
    }

}
