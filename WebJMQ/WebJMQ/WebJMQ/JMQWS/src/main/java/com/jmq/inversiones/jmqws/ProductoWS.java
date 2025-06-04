/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jmq.inversiones.jmqws;
import com.jmq.inversiones.business.ProductoService;
import com.jmq.inversiones.business.impl.ProductoServiceImpl;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.daoimpl.ProductoDAOImpl;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;
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
}
