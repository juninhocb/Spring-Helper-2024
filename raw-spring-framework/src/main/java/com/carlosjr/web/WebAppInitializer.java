package com.carlosjr.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.context.ApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

// Enable to test within spring mvc env - avoids - INFO: No Spring WebApplicationInitializer types detected on classpath
public class WebAppInitializer /*implements WebApplicationInitializer*/ {

    private final ApplicationContext applicationContext;

    public WebAppInitializer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    //@Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.setApplicationContext(applicationContext);
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");

        registration.addMapping("/products/*");
    }

}

