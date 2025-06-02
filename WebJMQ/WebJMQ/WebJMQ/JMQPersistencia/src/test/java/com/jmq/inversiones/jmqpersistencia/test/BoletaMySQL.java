package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.BoletaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.BoletaDAOImpl;
import com.jmq.inversiones.dominio.pagos.Boleta;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.dominio.pagos.MetodoPago;
import com.jmq.inversiones.dominio.usuario.Usuario;
import com.jmq.inversiones.dominio.ventas.EstadoCompra;
import com.jmq.inversiones.jmqpersistencia.dao.OrdenVentaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.OrdenVentaDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoletaMySQL {

    private BoletaDAO boletaDAO;
    private OrdenVentaDAO ordenVentaDAO;
    @BeforeEach
    public void setUp() {
        boletaDAO = new BoletaDAOImpl(); // Asegúrate que este DAO se conecta o está simulado correctamente
        ordenVentaDAO = new OrdenVentaDAOImpl();
    }

    @Test
    public void testAgregarYObtener() {
        Boleta bol = crearBoletaEjemplo();

        boletaDAO.agregar(bol);
        
        Boleta obtenido = boletaDAO.obtener(bol.getId());

          // Verificaciones
        assertNotNull(obtenido, "La boleta no fue encontrada luego de insertarse");
        assertEquals("Cliente Test", obtenido.getNombre());
        assertEquals("12345678", obtenido.getDni());
        assertEquals(bol.getMetodoPago(), obtenido.getMetodoPago());
        assertEquals(bol.getMonto_total(), obtenido.getMonto_total(), 0.01);
    }

    @Test
    public void testListarTodos() {
//        boletaDAO.agregarHeredado(crearBoletaEjemplo(8));
//        boletaDAO.agregarHeredado(crearBoletaEjemplo(9));

        List<Boleta> boletas = boletaDAO.listarTodos();
        System.out.println(boletas.size());
//        assertTrue(boletas.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Boleta bol = boletaDAO.obtener(51);

        bol.setNombre("Cliente Actualizado");
        boletaDAO.actualizar(bol);

        Boleta actualizado = boletaDAO.obtener(51);
        assertEquals("Cliente Actualizado", actualizado.getNombre());
    }

    @Test
    public void testEliminar() {
//        Boleta bol = crearBoletaEjemplo(5);
//        boletaDAO.agregar(bol);

        boletaDAO.eliminar(52);
        assertNull(boletaDAO.obtener(52));
    }

    private Boleta crearBoletaEjemplo() {
        OrdenVenta ordenDummy = new OrdenVenta();
        ordenDummy.setEstado_compra(EstadoCompra.pagado); // o el estado que desees
        ordenDummy.setFecha_orden(new Date());
        ordenDummy.setActivo(true);

        Usuario u = new Usuario(); // ⚠️ ID debe existir
        u.setId(1);
        ordenDummy.setUsuario(u);

        ordenVentaDAO.agregar(ordenDummy); // se guarda y se genera el ID
        int idGenerado = ordenDummy.getId();

        Boleta boleta = new Boleta();
        boleta.setOrden(ordenDummy); // ya no es null
        boleta.setMetodoPago(MetodoPago.tarjeta);
        boleta.setFecha_pago(new Date());
        boleta.setMonto_total(250.0);
        boleta.setDni("12345678");
        boleta.setNombre("Cliente Test");
        boleta.setFecha_emision(new Date());
        return boleta;
    }

}
