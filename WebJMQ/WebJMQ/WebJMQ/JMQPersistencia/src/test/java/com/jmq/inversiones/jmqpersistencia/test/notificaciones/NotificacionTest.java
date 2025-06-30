package com.jmq.inversiones.jmqpersistencia.test.notificaciones;

import com.jmq.inversiones.dominio.notificaciones.Notificacion;
import com.jmq.inversiones.jmqpersistencia.dao.notificaciones.NotificacionDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.notificaciones.NotificacionDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificacionTest {

    private NotificacionDAO notificacionDAO;

    @BeforeEach
    public void setUp() {
        notificacionDAO = new NotificacionDAOImpl();
    }

    @Test
    public void testAgregarYListar() {
        Notificacion noti = crearNotificacionEjemplo();
        notificacionDAO.agregar(noti);

        List<Notificacion> lista = notificacionDAO.listarTodos();
        boolean encontrado = lista.stream()
                .anyMatch(n -> n.getMensaje().equals(noti.getMensaje()) &&
                               n.getTipo().equals(noti.getTipo()));

        assertTrue(encontrado);
    }

    @Test
    public void testActualizar() {
        Notificacion noti = crearNotificacionEjemplo();
        notificacionDAO.agregar(noti);

        noti.setEstado("leído");
        noti.setMensaje("Mensaje actualizado");
        notificacionDAO.actualizar(noti);

//        

        assertNotNull(noti);
//        
    }

    @Test
    public void testEliminar() {
        Notificacion noti = crearNotificacionEjemplo();
        notificacionDAO.agregar(noti);

        notificacionDAO.eliminar(noti.getId());

//        
    }

    private Notificacion crearNotificacionEjemplo() {
        Notificacion n = new Notificacion();
        n.setTipo("Sistema");
        n.setMensaje("Este es un mensaje de prueba");
        n.setFecha_envio(new Date());
        n.setEstado("pendiente");
        n.setAsunto("Hola");
        return n;
    }
}
