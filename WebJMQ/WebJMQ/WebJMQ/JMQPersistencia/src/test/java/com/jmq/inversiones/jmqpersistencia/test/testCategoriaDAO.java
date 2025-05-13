package com.jmq.inversiones.jmqpersistencia.test;

import com.jmq.inversiones.jmqpersistencia.dao.CategoriaDAO;
import com.jmq.inversiones.jmqpersistencia.modelo.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class testCategoriaDAO {

    private CategoriaDAO categoriaDAO;

    @BeforeEach
    public void setUp() {
        categoriaDAO = new CategoriaDAO(); // Asegúrate que esta clase tenga implementación válida
    }

    @Test
    public void testAgregarYObtener() {
        Categoria cat = new Categoria();
        cat.setIdCategoria(1);
        cat.setNombre("Electrónica");
        cat.setDescripcion("Dispositivos tecnológicos");
        cat.setIdDescuento(1);

        categoriaDAO.agregar(cat);
        Categoria obtenido = categoriaDAO.obtener(1);

        assertNotNull(obtenido);
        assertEquals("Electrónica", obtenido.getNombre());
    }

    @Test
    public void testListarTodos() {
        Categoria c1 = new Categoria();
        c1.setIdCategoria(1);
        c1.setNombre("A");

        Categoria c2 = new Categoria();
        c2.setIdCategoria(2);
        c2.setNombre("B");

        categoriaDAO.agregar(c1);
        categoriaDAO.agregar(c2);

        List<Categoria> categorias = categoriaDAO.listarTodos();
        assertTrue(categorias.size() >= 2);
    }

    @Test
    public void testActualizar() {
        Categoria cat = new Categoria();
        cat.setIdCategoria(1);
        cat.setNombre("Oficina");

        categoriaDAO.agregar(cat);

        cat.setNombre("Oficina y Papelería");
        categoriaDAO.actualizar(cat);

        Categoria actualizado = categoriaDAO.obtener(1);
        assertEquals("Oficina y Papelería", actualizado.getNombre());
    }

    @Test
    public void testEliminar() {
        Categoria cat = new Categoria();
        cat.setIdCategoria(1);
        cat.setNombre("Eliminar");

        categoriaDAO.agregar(cat);
        categoriaDAO.eliminar(1);

        assertNull(categoriaDAO.obtener(1));
    }
}
