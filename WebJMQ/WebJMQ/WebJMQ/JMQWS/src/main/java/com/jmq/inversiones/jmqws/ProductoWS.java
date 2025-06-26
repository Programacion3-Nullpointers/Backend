/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmq.inversiones.jmqws;
import com.jmq.inversiones.business.ProductoService;
import com.jmq.inversiones.business.impl.ProductoServiceImpl;
import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ProductoDAOImpl;
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
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author LUIS
 */
@WebService(serviceName = "ProductoWS")
public class ProductoWS {
    private final ProductoService productoService;
    
    public ProductoWS(){
        this.productoService = new ProductoServiceImpl(new ProductoDAOImpl());
    }
    
    @WebMethod
    public void registrarProducto(@WebParam(name = "producto") Producto producto) throws Exception{
        productoService.registrarProducto(producto);
    }
    @WebMethod
    public void actualizarProducto(@WebParam(name = "producto") Producto producto) throws Exception{
        productoService.actualizarProducto(producto);
    }
    @WebMethod
    public void eliminarProducto(@WebParam(name = "id") int id) throws Exception{
        productoService.eliminarProducto(id);
    }
    @WebMethod
    public Producto buscarProducto(@WebParam(name ="id") int id) throws Exception{
        return productoService.buscarProducto(id);
    }
    @WebMethod
    public List<Producto> listaProducto() throws Exception{
        return productoService.listarProductos();
    }
    @WebMethod
    public void descontarStock(@WebParam(name ="id") int id, 
            @WebParam(name ="stock") int stock) throws Exception{
        productoService.descontarStock(id, stock);
    }
    @WebMethod
    public List<Producto> filtrarProductos(
            @WebParam(name = "nombreCategoria") String nombreCategoria,
            @WebParam(name = "activo") Boolean activo,
            @WebParam(name = "precioMin") Double precioMin,
            @WebParam(name = "precioMax") Double precioMax,
            @WebParam(name = "stockMin") Integer stockMin,
            @WebParam(name = "stockMax") Integer stockMax,
            @WebParam(name = "conDescuento") Boolean conDescuento
    ) throws Exception{
        return productoService.filtrarProductos(nombreCategoria, activo, precioMin, precioMax, stockMin, stockMax,conDescuento);
    }

    
    @WebMethod(operationName = "reporteMasVendidos")
    public byte[] reporteMasVendidos(){
        try{
            Map<String, Object> params = new HashMap<>();  
            params.put("logo",ImageIO.read(new File(getFileResource("logo1.png"))));
            String fileXML = getFileResource("Productos.jrxml");
            return generarBufferFromJP(fileXML, params);
        }catch(Exception ex){
            throw new WebServiceException("Error al generar report: " + ex.getMessage());
        }
    }
    
    private String generarQuery(Integer args[]){
        return "hola";
    }
    
    @WebMethod(operationName = "reporteStock")
    public byte[] reporteStock(Integer[] args){
        String query;
        try{
            Map<String, Object> params = new HashMap<>();  
            params.put("logo",ImageIO.read(new File(getFileResource("logo1.png"))));
            String fileXML = getFileResource("Stock.jrxml");
            if (args != null) {
                query = generarQuery(args);
                JasperDesign jasperDesign = JRXmlLoader.load("Stock.jrxml");
                JRDesignQuery nuevaQuery = new JRDesignQuery();
                nuevaQuery.setText(query);
                jasperDesign.setQuery(nuevaQuery);
                // Compilar el diseño modificado
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                //falta hacer que funcione de verdad :(
                return generarBufferFromJP(fileXML, params);
            }            
            else return generarBufferFromJP(fileXML, params);
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
    
    private byte[] generarBufferFromJPConCambioDeQuery(String inFileXML, Map<String, Object> params, String query) throws JRException {
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
