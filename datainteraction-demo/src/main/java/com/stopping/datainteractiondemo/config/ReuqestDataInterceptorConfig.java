package com.stopping.datainteractiondemo.config;

import com.stopping.datainteractiondemo.interceptor.RequestDataInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ReuqestDataInterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new RequestDataInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
