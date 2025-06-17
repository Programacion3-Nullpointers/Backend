package com.jmq.inversiones.jmqrestserver.resources;

import com.jmq.inversiones.business.impl.NotificacionServiceImpl;
import com.jmq.inversiones.business.EmailService;
import com.jmq.inversiones.business.NotificacionService;
import com.jmq.inversiones.dominio.notificaciones.Notificacion;
import com.jmq.inversiones.jmqpersistencia.daoimpl.NotificacionDAOImpl;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("notificaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class NotificacionRS {

    private final NotificacionService servicio;

    public NotificacionRS() {
        servicio = new NotificacionServiceImpl(new NotificacionDAOImpl(), new DummyEmailService());
    }

    @POST
    public Response registrar(Notificacion notificacion) {
        try {
            servicio.registrarNotificacion(notificacion);
            return Response.ok(notificacion).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") int id) {
        try {
            Notificacion n = servicio.buscarNotificacion(id);
            if (n == null) return Response.status(Response.Status.NOT_FOUND).build();
            return Response.ok(n).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // Agrega más métodos si deseas (listar, eliminar, actualizar)
}

