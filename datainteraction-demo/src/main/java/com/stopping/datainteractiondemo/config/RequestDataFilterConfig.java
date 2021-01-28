package com.stopping.datainteractiondemo.config;

import com.stopping.datainteractiondemo.Filter.ReuqestDataFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestDataFilterConfig {
    @Autowired
    private ReuqestDataFilter reuqestDataFilter;

    @Bean
    public FilterRegistrationBean initFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(reuqestDataFilter);
        registrationBean.setName("reuqestDataFilter");
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
