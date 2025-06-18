package com.jmq.inversiones.jmqrestserver.resources;

import com.jmq.inversiones.business.impl.EmailServiceImpl;
import com.jmq.inversiones.business.impl.UsuarioServiceImpl;
import com.jmq.inversiones.business.UsuarioService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/usuario")
public class UsuarioRS {

    private final UsuarioService usuarioService = null; //por el momento queda ASI
    
    @POST
    @Path("/recuperar-password")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recuperarPassword(String correo) {
        try {
            usuarioService.iniciarRecuperacionPassword(correo);
            return Response.ok("Correo de recuperación enviado").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
