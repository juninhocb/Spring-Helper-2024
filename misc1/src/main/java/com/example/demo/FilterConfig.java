package com.example.demo;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FilterConfig {
    @Bean
    FilterRegistrationBean<CustomFilter> customFilterRegistry(CustomFilter customFilter) {
        FilterRegistrationBean<CustomFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(customFilter);
        filterRegistrationBean.addUrlPatterns("/stub/*");
        //filterRegistrationBean.addUrlPatterns("/*"); //all paths
        return filterRegistrationBean;
    }
}
