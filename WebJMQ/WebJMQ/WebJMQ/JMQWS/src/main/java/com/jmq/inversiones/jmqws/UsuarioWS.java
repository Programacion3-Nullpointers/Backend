package com.jmq.inversiones.jmqws;

import com.jmq.inversiones.business.UsuarioService;
import com.jmq.inversiones.business.impl.UsuarioServiceImpl;
import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.usuario.Usuario;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@WebService(serviceName = "UsuarioWS")
public class UsuarioWS {

    private final UsuarioService usuarioService;
    
    public UsuarioWS(){
        this.usuarioService = new UsuarioServiceImpl();
    }

    @WebMethod(operationName = "registrarUsuario")
    public Usuario registrarUsuario(@WebParam(name = "usuario") Usuario usuario) throws Exception {
        usuarioService.registrarUsuario(usuario);
        return usuarioService.buscarUsuario(usuario.getId());
    }

    @WebMethod(operationName = "actualizarUsuario")
    public void actualizarUsuario(@WebParam(name = "usuario") Usuario usuario) throws Exception {
        usuarioService.actualizarUsuario(usuario);
    }

    @WebMethod(operationName = "eliminarUsuario")
    public void eliminarUsuario(@WebParam(name = "id") int id) throws Exception {
        usuarioService.eliminarUsuario(id);
    }

    @WebMethod(operationName = "buscarUsuario")
    public Usuario buscarUsuario(@WebParam(name = "id") int id) throws Exception {
        return usuarioService.buscarUsuario(id);
    }

    @WebMethod(operationName = "listarUsuarios")
    public List<Usuario> listarUsuarios() throws Exception {
        return usuarioService.listarUsuarios();
    }
    
    @WebMethod(operationName = "BuscarUsuarioPorCorreo")
    public Usuario buscarUsuarioPorCorreo(@WebParam(name = "correo") String correo) throws Exception {
        return usuarioService.buscarUsuarioPorCorreo(correo);
    }
    
     @WebMethod
    public void iniciarRecuperacionPassword(String email) throws Exception {
        usuarioService.iniciarRecuperacionPassword(email);
    }

    @WebMethod
    public boolean cambiarPasswordConToken(
        @WebParam(name = "token") String token,
        @WebParam(name = "nuevaPassword") String nuevaPassword
    ) throws Exception {
        return usuarioService.cambiarPasswordConToken(token, nuevaPassword);
    }
    
    @WebMethod
    public boolean validarTokenPassword(String token) {
        return usuarioService.validarTokenPassword(token);
    }

    
    @WebMethod(operationName = "reporteClientes")
    public byte[] reporteClientes(){
        try{
            Map<String, Object> params = new HashMap<>();  
            params.put("logo",ImageIO.read(new File(getFileResource("logo1.png"))));
            String fileXML = getFileResource("RClientes.jrxml");            
            return generarBufferFromJP(fileXML, params);
        }catch(Exception ex){
            throw new WebServiceException("Error al generar report: " + ex.getMessage());
        }
    }
    
    private String getFileResource(String fileName){ 
        String filePath = getClass().getClassLoader().getResource(fileName).getPath();
        filePath = filePath.replace("%20", " ");
        return filePath;
    }

    private byte[] generarBufferFromJP(String inFileXML, Map<String, Object> params) throws JRException {
         //Se compila una sola vez
        String fileJasper = inFileXML +".jasper";
        if(!new File(fileJasper).exists()){
            //para compilar en GlassFish se requiere las librerias: jasperreports-jdt, ecj
            JasperCompileManager.compileReportToFile(inFileXML, fileJasper);         
        }
        //1- leer el archivo compilado
        JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile(fileJasper);
        //2- poblar el reporte
        try (Connection conn = DBManager.getInstance().obtenerConexion()){
            JasperPrint jp = JasperFillManager.fillReport(jr,params, conn);          
            return JasperExportManager.exportReportToPdf(jp);
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar entidad", e);
        }
    }
}
 