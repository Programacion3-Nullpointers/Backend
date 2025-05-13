package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.NotificacionDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.Notificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testNotificacionDAO {

    private NotificacionDAO notificacionDAO;

    @BeforeEach
    public void setUp() {
        notificacionDAO = new NotificacionDAO(); // Asegúrate que esté correctamente implementado
    }

    @Test
    public void testAgregarYObtener() {
        Notificacion noti = new Notificacion();
        noti.setIdNotificacion(1);
        noti.setTipo("INFO");
        noti.setMensaje("Compra realizada");
        noti.setFecha_envio(Timestamp.valueOf("2025-05-13 12:00:00"));
        noti.setEstado("Enviado");

        notificacionDAO.agregar(noti);
        Notificacion obtenido = notificacionDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("Compra realizada", obtenido.getMensaje());
    }

    @Test
    public void testListarTodos() {
        Notificacion n1 = new Notificacion();
        n1.setIdNotificacion(2);
        n1.setTipo("ALERTA");
        n1.setMensaje("Pago fallido");
        n1.setFecha_envio(Timestamp.valueOf("2025-05-13 13:00:00"));
        n1.setEstado("Pendiente");

        Notificacion n2 = new Notificacion();
        n2.setIdNotificacion(3);
        n2.setTipo("INFO");
        n2.setMensaje("Producto enviado");
        n2.setFecha_envio(Timestamp.valueOf("2025-05-13 14:00:00"));
        n2.setEstado("Entregado");

        notificacionDAO.agregar(n1);
        notificacionDAO.agregar(n2);

        List<Notificacion> notificaciones = notificacionDAO.listarTodos();
        assertTrue(notificaciones.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Notificacion noti = new Notificacion();
        noti.setIdNotificacion(4);
        noti.setTipo("INFO");
        noti.setMensaje("Mensaje temporal");
        noti.setFecha_envio(Timestamp.valueOf("2025-05-13 15:00:00"));
        noti.setEstado("Borrador");

        notificacionDAO.agregar(noti);

        noti.setEstado("Enviado");
        notificacionDAO.actualizar(noti);

        Notificacion actualizado = notificacionDAO.obtener(4);
        assertEquals("Enviado", actualizado.getEstado());
    }

    @Test
    public void testEliminar() {
        Notificacion noti = new Notificacion();
        noti.setIdNotificacion(5);
        noti.setTipo("ALERTA");
        noti.setMensaje("Eliminar prueba");
        noti.setFecha_envio(Timestamp.valueOf("2025-05-13 16:00:00"));
        noti.setEstado("Eliminado");

        notificacionDAO.agregar(noti);
        notificacionDAO.eliminar(5);

        assertNull(notificacionDAO.obtener(5));
    }
}
