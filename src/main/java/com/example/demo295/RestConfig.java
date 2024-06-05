package com.example.demo295;

import com.example.demo295.service.RoomController;
import com.example.demo295.util.exceptionhandler.FormatErrorExceptionHandler;
import com.example.demo295.util.exceptionhandler.NotFoundExceptionHandler;
import com.example.demo295.service.ModuleController;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationPath("/resources")
public class RestConfig extends Application {
    public Set<Class<?>> getClasses() {
        return new HashSet<>(List.of(
                ModuleController.class,
                RoomController.class,
                NotFoundExceptionHandler.class,
                FormatErrorExceptionHandler.class,
                AuthenticationFilter.class));
    }
}
