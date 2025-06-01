package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.notificaciones.Notificacion;
import com.jmq.inversiones.jmqpersistencia.dao.NotificacionDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.NotificacionDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificacionMySQL {

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

        List<Notificacion> lista = notificacionDAO.listarTodos();
        Notificacion actualizado = lista.stream()
                .filter(n -> n.getId() == noti.getId())
                .findFirst().orElse(null);

        assertNotNull(actualizado);
        assertEquals("leído", actualizado.getEstado());
        assertEquals("Mensaje actualizado", actualizado.getMensaje());
    }

    @Test
    public void testEliminar() {
        Notificacion noti = crearNotificacionEjemplo();
        notificacionDAO.agregar(noti);

        notificacionDAO.eliminar(noti.getId());

        List<Notificacion> lista = notificacionDAO.listarTodos();
        boolean eliminado = lista.stream()
                .noneMatch(n -> n.getId() == noti.getId());

        assertTrue(eliminado);
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
