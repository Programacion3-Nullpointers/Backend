package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.usuario.TipoUsuario;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.jmqpersistencia.dao.UsuarioDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.UsuarioDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioMySQL {

    private UsuarioDAO usuarioDAO;

    @BeforeEach
    public void setUp() {
        usuarioDAO = new UsuarioDAOImpl();
    }

    @Test
    public void testAgregarYListar() {
        Usuario nuevo = crearUsuarioEjemplo();
        usuarioDAO.agregar(nuevo);

        List<Usuario> usuarios = usuarioDAO.listarTodos();
        assertNotNull(usuarios);
        assertTrue(usuarios.stream().anyMatch(u -> u.getId() == nuevo.getId()));
    }
    
    @Test
    public void BuscarUsuario(){
        Usuario usuario = usuarioDAO.obtener(1);
        
        if(usuario == null){
            System.out.println("El usuario es vacio");
        }
        else{
            System.out.println("El usuario esta lleno");
            System.out.println(usuario);
        }
        assertNotNull(usuario);
    }
    @Test
    public void testActualizar() {
        Usuario usu = crearUsuarioEjemplo();
        usuarioDAO.agregar(usu);

        usu.setCorreo("nuevo_correo@test.com");
        usuarioDAO.actualizar(usu);
        usuarioDAO.obtener(usu.getId()); //prueba el obtener 
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        Usuario actualizado = usuarios.stream()
                .filter(u -> u.getId() == usu.getId())
                .findFirst()
                .orElse(null);

        assertNotNull(actualizado);
        assertEquals("nuevo_correo@test.com", actualizado.getCorreo());
    }

    @Test
    public void testEliminar() {
        Usuario usu = crearUsuarioEjemplo();
        usuarioDAO.agregar(usu);

        usuarioDAO.eliminar(usu.getId());

        List<Usuario> usuarios = usuarioDAO.listarTodos();
        assertFalse(usuarios.stream().anyMatch(u -> u.getId() == usu.getId()));
    }
    @Test
    public void testBuscarCorreo(){
        Usuario usuario = usuarioDAO.obtenerPorCorreo("admin@admin.com");
         if(usuario == null){
            System.out.println("El usuario es vacio");
        }
        else{
            System.out.println("El usuario esta lleno");
            System.out.println(usuario);
        }
        assertNotNull(usuario);
    }
    private Usuario crearUsuarioEjemplo() {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario("jose");
//        String contra = usuarioDAO.hashCode();
        usuario.setContrasena("123456");
        usuario.setActivo(true);
        usuario.setCorreo("cliente@test.com");
        usuario.setTipoUsuario(TipoUsuario.CLIENTE);
        usuario.setRazonsocial("N/A");
        usuario.setDireccion("Calle Falsa 123");
        usuario.setRUC("12345678901");
        return usuario;
    }
}
