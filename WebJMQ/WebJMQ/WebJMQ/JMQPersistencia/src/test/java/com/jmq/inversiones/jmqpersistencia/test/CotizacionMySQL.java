package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.dominio.cotizaciones.Cotizacion;
import com.jmq.inversiones.dominio.cotizaciones.ProductoCotizacion;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.jmqpersistencia.dao.CotizacionDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.CotizacionDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CotizacionMySQL {

    private CotizacionDAO cotizacionDAO;

    @BeforeEach
    public void setUp() {
        cotizacionDAO = new CotizacionDAOImpl(); // corrige a CotizacionDAOImpl si renombraste la clase
    }

    @Test
    public void testAgregarYObtener() {
        Cotizacion cot = crearCotizacionEjemplo(1);
        cotizacionDAO.agregar(cot);

        Cotizacion obtenido = cotizacionDAO.obtener(cot.getId());

        assertNotNull(obtenido);
        assertEquals("En proceso", obtenido.getEstadoCotizacion());
        //assertEquals(1, obtenido.getProductos().size());
    }

    @Test
    public void testActualizar() {
        Cotizacion cot = crearCotizacionEjemplo(2);
        cotizacionDAO.agregar(cot);

        cot.setEstadoCotizacion("Revisado");
        //cot.getProductos().get(0).setCantidad(10);
        cotizacionDAO.actualizar(cot);

        Cotizacion actualizado = cotizacionDAO.obtener(cot.getId());
        assertEquals("Revisado", actualizado.getEstadoCotizacion());
        //assertEquals(10, actualizado.getProductos().get(0).getCantidad());
    }

    @Test
    public void testEliminar() {
        Cotizacion cot = crearCotizacionEjemplo(3);
        cotizacionDAO.agregar(cot);

        cotizacionDAO.eliminar(cot.getId());

        Cotizacion eliminado = cotizacionDAO.obtener(cot.getId());
        assertEquals(eliminado.isActivo(),false);
    }

    private Cotizacion crearCotizacionEjemplo(int dummyId) {
        Usuario u = new Usuario();
        u.setId(1); // ⚠️ Este usuario debe existir en la BD

//        ProductoCotizacion pc = new ProductoCotizacion();
//        pc.setId(dummyId);
//        pc.setDescripcion("Producto ");
//        pc.setCantidad(1);
//        pc.setPrecioCotizado(100.0);
//
//        List<ProductoCotizacion> productos = new ArrayList<>();
//        productos.add(pc);

        Cotizacion cot = new Cotizacion();
        cot.setUsuario(u);
        cot.setEstadoCotizacion("En proceso");
       // cot.setProductos(productos);

        return cot;
    }
}
