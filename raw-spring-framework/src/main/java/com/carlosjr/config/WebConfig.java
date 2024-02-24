package com.carlosjr.config;

import jakarta.annotation.PostConstruct;
import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;


@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;

    public WebConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    void afterInjection(){
        try {
            Tomcat tomcat = new Tomcat();
            tomcat.setPort(8080); // overriding default

            String webAppDirLocation = "src/main/java/com/carlosjr/webapp";


            StandardContext servletCtx = (StandardContext) tomcat.addWebapp("", new File(webAppDirLocation).getAbsolutePath());
            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.setApplicationContext(applicationContext);
            tomcat.addServlet((Context) servletCtx, "dispatcher", dispatcherServlet);
            servletCtx.addServletMappingDecoded("/*", "dispatcher");
            System.out.println("Servlet container ::: " + servletCtx.getBaseName());

            tomcat.start();
            tomcat.getServer().await();
        }catch (Exception ex){
            System.out.println(ex.getMessage() + " ::: " + ex.getCause());
        }
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
