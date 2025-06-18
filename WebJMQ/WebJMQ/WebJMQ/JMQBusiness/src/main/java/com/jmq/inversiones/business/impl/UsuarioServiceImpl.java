package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.EmailService;
import com.jmq.inversiones.business.UsuarioService;
import com.jmq.inversiones.dominio.usuario.TipoUsuario;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.jmqpersistencia.dao.UsuarioDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.UsuarioDAOImpl;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import static java.util.Objects.hash;
import java.util.UUID;

public class UsuarioServiceImpl implements UsuarioService{
    
        private final UsuarioDAO usuarioDAO;
        private final EmailService emailService;
    // Constructor que recibe el DAO (Inyección de dependencias)
    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.emailService = new EmailServiceImpl();
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
    public void actualizarUsuario(Usuario usuario) throws Exception {
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
                throw new Exception("El correo es requerido");
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

    @Override
    public Usuario buscarUsuarioPorCorreo(String correo) throws Exception {
        try{
            if(!correo.isEmpty()){
            } else {
                throw new Exception("");
            }
            return usuarioDAO.obtenerPorCorreo(correo);
        }
        catch (Exception ex){
            throw new Exception(""+ex.getMessage());
        }
    }

    @Override
    public void iniciarRecuperacionPassword(String correo) throws Exception {
        if (correo == null || correo.trim().isEmpty()) {
            throw new Exception("Debe ingresar un correo.");
        }
         Usuario usuario = usuarioDAO.obtenerPorCorreo(correo);
        if (usuario == null) throw new RuntimeException("El correo ingresado no está registrado"); //mensaje que se mostrará en el frontend
        //genera token
        String token = UUID.randomUUID().toString();
        usuario.setToken_reset(token);
        LocalDateTime expira = LocalDateTime.now().plusHours(1);
        Date fechaExp = Date.from(expira.atZone(ZoneId.systemDefault()).toInstant());
        usuario.setFecha_expiracion_token(fechaExp);
        usuarioDAO.actualizar(usuario);

        emailService.enviarRecuperacionPassword(usuario); // Debe enviar email con enlace
    }

    @Override
    public Usuario obtenerPorToken(String token) throws Exception {
        Usuario usuario = usuarioDAO.obtenerPorToken(token);
        if (usuario == null || usuario.getFecha_expiracion_token().before(new Date())) {
            throw new Exception("Token inválido o expirado");
        }
        return usuario;
    }

    @Override
    public boolean cambiarPasswordConToken(String token, String nuevaPassword) throws Exception {
        Usuario usuario = usuarioDAO.obtenerPorToken(token);
        if (usuario == null || 
            usuario.getFecha_expiracion_token().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .isBefore(LocalDateTime.now())) {
            return false;
        }
        usuarioDAO.actualizar(usuario);
        return true;
        }
}

