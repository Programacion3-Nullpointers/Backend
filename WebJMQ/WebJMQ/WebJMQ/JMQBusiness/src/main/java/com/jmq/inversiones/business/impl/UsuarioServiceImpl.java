package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.EmailService;
import com.jmq.inversiones.business.UsuarioService;
import com.jmq.inversiones.dominio.usuario.TipoUsuario;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.jmqpersistencia.dao.UsuarioDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.UsuarioDAOImpl;
import java.util.Date;
import java.util.List;
import io.jsonwebtoken.*;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioServiceImpl implements UsuarioService{
    
        private final UsuarioDAO usuarioDAO;
        private final EmailService emailService;
        private static final String SECRET_KEY = "U3Jucj5rM2pQaUtmZ1BzR1pLQXp4YW5VNjg0MjM0NTY=";
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
            
            if (usuario.getContrasena().trim().length() < 8) {
                throw new Exception("La contraseña debe tener al menos 8 caracteres");
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

            String hashedPassword = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt());
            usuario.setContrasena(hashedPassword);

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
            String hashedPassword = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt());
            usuario.setContrasena(hashedPassword);

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
        if (correo == null || correo.trim().isEmpty()) {
            throw new Exception("Debe ingresar un correo válido");
        }

        Usuario usuario = usuarioDAO.obtenerPorCorreo(correo.trim());
        if (usuario == null) {
            throw new Exception("El correo ingresado no está registrado");
        }

        return usuario;
    }


    @Override
    public void iniciarRecuperacionPassword(String correo) throws Exception {
        if (correo == null || correo.isEmpty()) {
            throw new Exception("Debe ingresar un correo.");
        }

        Usuario usuario = usuarioDAO.obtenerPorCorreo(correo);
        if (usuario == null) {
            throw new Exception("El correo ingresado no está registrado");
        }

        String token = generarToken(usuario.getCorreo());
        String link = "https://localhost:44356/Login/Restablecer.aspx?token="+token; // actualiza con ruta real
        
        emailService.enviarRecuperacionPassword(usuario.getCorreo(), usuario.getNombreUsuario(), link);
    }
    
    private String generarToken(String email) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + 15 * 60 * 1000); // 15 minutos

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }
    
    private String validarToken(String token) throws Exception {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // correo
        } catch (ExpiredJwtException e) {
            throw new Exception("Token expirado");
        } catch (JwtException e) {
            throw new Exception("Token inválido");
        }
    }
    
    @Override
    public boolean cambiarPasswordConToken(String token, String nuevaPassword) throws Exception {
        String correo = validarToken(token);
        Usuario usuario = usuarioDAO.obtenerPorCorreo(correo);
        if (usuario == null) throw new Exception("Usuario no encontrado");

        // Comparar la nueva con la actual, usando BCrypt
        if (BCrypt.checkpw(nuevaPassword, usuario.getContrasena())) {
            throw new Exception("No puede utilizar su misma contraseña.");
        }

        // Cifrar la nueva contraseña
        String hashedPassword = BCrypt.hashpw(nuevaPassword, BCrypt.gensalt());
        usuario.setContrasena(hashedPassword);
        usuarioDAO.actualizar(usuario);
        return true;
    }
    
    @Override
    public boolean validarTokenPassword(String token) {
        try {
            validarToken(token); // ya tenés este método privado implementado
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public List<Usuario> filtrarUsuarios(String tipoEntidad, Boolean activo) throws SQLException {
        try {
            return usuarioDAO.filtrarUsuarios(tipoEntidad, activo);
        } catch (SQLException e){
            throw e;
        }
    }
}

