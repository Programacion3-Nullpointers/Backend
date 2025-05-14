package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.CategoriaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.CategoriaDAOImpl;
import com.jmq.inversiones.dominio.ventas.Categoria;
import com.jmq.inversiones.dominio.pagos.Descuento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testCategoriaDAO {

    private CategoriaDAO categoriaDAO;

    @BeforeEach
    public void setUp() {
        categoriaDAO = new CategoriaDAOImpl(); // DAO funcional que usa SP
    }

    @Test
    public void testAgregarYObtener() {
        Categoria cat = crearCategoriaEjemplo(1);

        categoriaDAO.agregar(cat);

        List<Categoria> lista = categoriaDAO.listarTodos();
        boolean encontrada = lista.stream().anyMatch(c -> c.getNombre().equals("Electrohogar Test"));

        assertTrue(encontrada);
    }

    @Test
    public void testActualizar() {
        Categoria cat = crearCategoriaEjemplo(2);
        categoriaDAO.agregar(cat);

        cat.setNombre("Categoría Modificada");
        categoriaDAO.actualizar(cat);

        List<Categoria> lista = categoriaDAO.listarTodos();
        boolean modificada = lista.stream().anyMatch(c -> c.getNombre().equals("Categoría Modificada"));

        assertTrue(modificada);
    }

    @Test
    public void testEliminar() {
        Categoria cat = crearCategoriaEjemplo(3);
        categoriaDAO.agregar(cat);

        categoriaDAO.eliminar(cat.getId());

        List<Categoria> lista = categoriaDAO.listarTodos();
        boolean eliminada = lista.stream().noneMatch(c -> c.getId() == cat.getId());

        assertTrue(eliminada);
    }

    private Categoria crearCategoriaEjemplo(int idDummy) {
        Descuento descuento = new Descuento();
        descuento.setId(1); // ⚠️ debe existir en BD; si no, crea un descuento válido primero

        Categoria cat = new Categoria();
        cat.setId(idDummy); // no obligatorio, SP te devuelve ID
        cat.setDescripcion("Descripción ejemplo");
        cat.setNombre("Electrohogar Test");
        cat.setDescuento(descuento);

        return cat;
    }
}
