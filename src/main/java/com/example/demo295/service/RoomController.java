package com.example.demo295.service;

import com.example.demo295.model.Room;
import com.example.demo295.repository.RoomDBRepository;
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
@Path("/api/rooms")
public class RoomController {
    @Autowired
    private RoomDBRepository roomRepository;
    Logger log = LoggerFactory.getLogger(LoggingController.class);

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRooms() {
        try {
            var rooms = roomRepository.findAll();
            log.warn("Rooms found!");

            if (rooms.isEmpty()) return Response.noContent().entity("No rooms found").build();

            return Response.ok().entity(rooms).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @PermitAll
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoom(@PathParam("id") int id) throws ModuleNotFoundException {
        var room = roomRepository.findById(id);

        if (room.isEmpty()) {
            throw new ModuleNotFoundException("No room found " + id);
        }

        return Response.ok().entity(room).build();
    }

    @POST
    @RolesAllowed("ADMIN")
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(@Validated Room room) {
        try {
            roomRepository.save(room);
            return Response.status(Response.Status.CREATED).entity(room).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @RolesAllowed("ADMIN")
    @Path("/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editRoom(@Validated Room room) {
        try {
            if (!roomRepository.existsById(room.getId())) {
                return Response.status(Response.Status.NOT_FOUND).entity("Room not found").build();
            }
            roomRepository.save(room);
            return Response.ok().entity(room).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRoom(@PathParam("id") int roomId) {
        try {
            var deletableRoom = roomRepository.findById(roomId);
            if (deletableRoom.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Room not found").build();
            }
            roomRepository.deleteById(deletableRoom.get().getId());
            return Response.ok().entity(deletableRoom.get()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}