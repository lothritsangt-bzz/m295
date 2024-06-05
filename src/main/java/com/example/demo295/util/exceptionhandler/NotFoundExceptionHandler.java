package com.example.demo295.util.exceptionhandler;

import com.example.demo295.util.exception.ModuleNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<ModuleNotFoundException> {
    @Override
    public Response toResponse(ModuleNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
    }
}