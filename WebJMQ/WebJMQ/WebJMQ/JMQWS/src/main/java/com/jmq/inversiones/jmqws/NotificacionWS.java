package com.jmq.inversiones.jmqws;

import com.jmq.inversiones.business.NotificacionService;
import com.jmq.inversiones.business.impl.EmailServiceImpl;
import com.jmq.inversiones.business.impl.NotificacionServiceImpl;
import com.jmq.inversiones.dominio.notificaciones.Notificacion;
import com.jmq.inversiones.jmqpersistencia.daoimpl.NotificacionDAOImpl;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;

import java.util.List;

@WebService(serviceName = "NotificacionWS")
public class NotificacionWS {

    private final NotificacionService servicio;

    public NotificacionWS() {
        servicio = new NotificacionServiceImpl(new NotificacionDAOImpl(), new EmailServiceImpl());
    }

    

}