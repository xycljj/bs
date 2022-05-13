package com.lyh.config;

import com.lyh.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lyh
 * @ClassName WebMvcConfiguration
 * @createTime 2021/12/16 15:15:00
 */
//@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor () {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration registration = registry.addInterceptor(loginInterceptor());//指定拦截器实例
        registration.addPathPatterns("/**");//用来指定那些请求
        registration.excludePathPatterns(
                "/test/user/login",
                "/test/admin/login"
        );//用来排除那些请求
    }

}
