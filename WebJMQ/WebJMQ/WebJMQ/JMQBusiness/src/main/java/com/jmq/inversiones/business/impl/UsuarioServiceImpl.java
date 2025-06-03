package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.UsuarioService;
import com.jmq.inversiones.dominio.usuario.TipoUsuario;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.jmqpersistencia.dao.UsuarioDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.UsuarioDAOImpl;
import java.util.List;

public class UsuarioServiceImpl implements UsuarioService{
    
        private final UsuarioDAO usuarioDAO;

    // Constructor que recibe el DAO (Inyección de dependencias)
    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    public void registrarUsuario(Usuario usuario) throws Exception {
        try {
            // Validaciones básicas antes de registrar
            if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty()) {
                throw new Exception("El nombre de usuario es requerido");
            }
            if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
                throw new Exception("La contraseña es requerida");
            }
            if (usuario.getCorreo()== null || usuario.getCorreo().isEmpty()) {
                throw new Exception("La correo es requerido");
            }
            // Asignar valores por defecto si es necesario
            if (usuario.getTipoUsuario() == null) {
                usuario.setTipoUsuario(TipoUsuario.CLIENTE); 
            }
           
            if(usuario.getTipoUsuario() == TipoUsuario.CLIENTE) {
                if(usuario.getDni() == null || usuario.getDni().isEmpty())
                    throw new Exception("El DNI es requerido para los clientes");
                if(usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty())
                    throw new Exception("El nombre de usuario es requerido para los clientes");
            }

            if(usuario.getTipoUsuario() == TipoUsuario.EMPRESA) {
                if(usuario.getRazonsocial() == null || usuario.getRazonsocial().isEmpty())
                    throw new Exception("La razón social es requerida para empresas");
                if(usuario.getRUC() == null || usuario.getRUC().isEmpty())
                    throw new Exception("El RUC es requerido para empresas");
            }

            
            usuarioDAO.agregar(usuario);
        } catch (Exception e) {
            throw new Exception("Error al registrar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualiarUsuario(Usuario usuario) throws Exception {
        try {
            if (usuario.getId() <= 0) {
                throw new Exception("ID de usuario inválido");
            }
            if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().isEmpty()) {
                throw new Exception("El nombre de usuario es requerido");
            }
            if (usuario.getContrasena() == null || usuario.getContrasena().isEmpty()) {
                throw new Exception("La contraseña es requerida");
            }
            if (usuario.getCorreo()== null || usuario.getCorreo().isEmpty()) {
                throw new Exception("La contraseña es requerida");
            }
            usuarioDAO.actualizar(usuario);
        } catch (Exception e) {
            throw new Exception("Error al actualizar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarUsuario(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de usuario inválido");
            }
            usuarioDAO.eliminar(id);
        } catch (Exception e) {
            throw new Exception("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario buscarUsuario(int id) throws Exception {
        try {
            if (id <= 0) {
                throw new Exception("ID de usuario inválido");
            }
            
            return usuarioDAO.obtener(id);
        } catch (Exception e) {
            throw new Exception("Error al buscar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> listarUsuarios() throws Exception {
        try {
            return usuarioDAO.listarTodos();
        } catch (Exception e) {
            throw new Exception("Error al listar usuarios: " + e.getMessage(), e);
        }
    }
}

