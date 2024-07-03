package com.example.demo;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        System.out.println("Until the filter process");

        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println("After the filter process");

    }
}
