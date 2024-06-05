package com.example.demo295;

import jakarta.servlet.ServletException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import jakarta.servlet.ServletContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {

        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();

        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.setInitParameter(
                "contextConfigLocation", "com.example.demo295");
    }
}
