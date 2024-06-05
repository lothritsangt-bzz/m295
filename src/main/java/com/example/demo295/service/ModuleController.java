package com.example.demo295.service;

import com.example.demo295.model.Modul;
import com.example.demo295.repository.ModuleDBRepository;
import com.example.demo295.util.exception.ModuleNotFoundException;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Path("/api/modules")
public class ModuleController {
    @Autowired
    private ModuleDBRepository moduleRepository;
    Logger log = LoggerFactory.getLogger(LoggingController.class);

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModules() {
        try {
            var modules = moduleRepository.findAll();
            log.warn("modules found!");

            if (modules.isEmpty()) return Response.noContent().entity("No modules found").build();

            return Response.ok().entity(modules).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @PermitAll
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") int id) throws ModuleNotFoundException {
        var module = moduleRepository.findById(id);

        if (module.isEmpty()){
            throw new ModuleNotFoundException("No module found "+id);
        }

        return Response.ok().entity(module).build();
    }

    @POST
    @RolesAllowed("ADMIN")
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createModule(@Validated Modul modul) {
        try {
            moduleRepository.save(modul);
            return Response.status(Response.Status.CREATED).entity(modul).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @RolesAllowed("ADMIN")
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editModule(@Validated Modul modul) {
        try {
            if (!moduleRepository.existsById(modul.getId())) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module not found").build();
            }
            moduleRepository.save(modul);
            return Response.ok().entity(modul).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteModule(@PathParam("id") int modulId) {
        try {
            var deletableModule = moduleRepository.findById(modulId);
            if (deletableModule.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module not found").build();
            }
            moduleRepository.deleteById(deletableModule.get().getId());
            return Response.ok().entity(deletableModule.get()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
