package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.UsuarioDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testUsuarioDAO {

    private UsuarioDAO usuarioDAO;

    @BeforeEach
    public void setUp() {
        usuarioDAO = new UsuarioDAO(); // Asegúrate que esta clase tenga implementación válida
    }

    @Test
    public void testAgregarYObtener() {
        Usuario user = new Usuario();
        user.setIdUsuario(1);
        user.setNombreUsuario("juan");
        user.setContrasena("1234");
        user.setActivo(1);
        user.setCorreo("juan@mail.com");
        user.setTipoUsuario("CLIENTE");
        user.setRazonsocial("Mi Empresa SAC");
        user.setDireccion("Av. Siempre Viva 123");
        user.setRUC("12345678901");

        usuarioDAO.agregar(user);
        Usuario obtenido = usuarioDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("juan", obtenido.getNombreUsuario());
    }

    @Test
    public void testListarTodos() {
        Usuario user1 = new Usuario();
        user1.setIdUsuario(1);
        user1.setNombreUsuario("a");

        Usuario user2 = new Usuario();
        user2.setIdUsuario(2);
        user2.setNombreUsuario("b");

        usuarioDAO.agregar(user1);
        usuarioDAO.agregar(user2);

        List<Usuario> usuarios = usuarioDAO.listarTodos();
        assertTrue(usuarios.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Usuario user = new Usuario();
        user.setIdUsuario(1);
        user.setNombreUsuario("juan");
        user.setCorreo("original@mail.com");

        usuarioDAO.agregar(user);

        user.setCorreo("nuevo@correo.com");
        usuarioDAO.actualizar(user);

        Usuario actualizado = usuarioDAO.obtener(1);
        assertEquals("nuevo@correo.com", actualizado.getCorreo());
    }

    @Test
    public void testEliminar() {
        Usuario user = new Usuario();
        user.setIdUsuario(1);
        user.setNombreUsuario("eliminar");

        usuarioDAO.agregar(user);
        usuarioDAO.eliminar(1);

        assertNull(usuarioDAO.obtener(1));
    }
}
