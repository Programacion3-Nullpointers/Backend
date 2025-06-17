package com.jmq.inversiones.jmqrestserver.resources;

import com.jmq.inversiones.business.impl.EmailServiceImpl;
import com.jmq.inversiones.business.impl.NotificacionServiceImpl;
import com.jmq.inversiones.dominio.notificaciones.Notificacion;
import com.jmq.inversiones.jmqpersistencia.daoimpl.NotificacionDAOImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/notificaciones")
public class NotificacionRS {

    private final NotificacionServiceImpl servicio = new NotificacionServiceImpl(
        new NotificacionDAOImpl(), new EmailServiceImpl());

    @POST
    @Path("/enviar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response enviar(Notificacion n) {
        try {
            servicio.enviarNotificacion(n);
            return Response.ok("Notificación enviada correctamente").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error: " + e.getMessage()).build();
        }
    }
}
