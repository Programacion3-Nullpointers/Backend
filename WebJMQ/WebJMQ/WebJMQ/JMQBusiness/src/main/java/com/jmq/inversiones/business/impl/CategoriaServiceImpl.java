package com.jmq.inversiones.business.impl;

import com.jmq.inversiones.business.CategoriaService;
import com.jmq.inversiones.dominio.ventas.Categoria;
import com.jmq.inversiones.jmqpersistencia.dao.CategoriaDAO;
import com.jmq.inversiones.jmqpersistencia.daoimpl.CategoriaDAOImpl;
import java.util.List;

public class CategoriaServiceImpl implements CategoriaService{
    
    private final CategoriaDAO categoriaDAO;
    
    public CategoriaServiceImpl(){
        this.categoriaDAO = new CategoriaDAOImpl();
    }

    @Override
    public void registrarCategoria(Categoria categoria) throws Exception {
        try {
            // Validar el descuento antes de registrar
            validarCategoria(categoria);
            
            // Llamar al DAO para insertar
            categoriaDAO.agregar(categoria);
            
        } catch (Exception e) {
            throw new Exception("Error al registrar categoria: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarCategoria(Categoria categoria) throws Exception {
        try {
            // Validaciones básicas
            if (categoria.getId() <= 0) {
                throw new Exception("ID de categoria inválido");
            }
            validarCategoria(categoria);
            
            // Verificar que la categoria exista
            Categoria existente = categoriaDAO.obtener(categoria.getId());
            if (existente == null) {
                throw new Exception("Categoria no encontrada");
            }
            
            // Llamar al DAO para actualizar
            categoriaDAO.actualizar(categoria);
            
        } catch (Exception e) {
            throw new Exception("Error al actualizar categoria: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminarCategoria(int id) throws Exception {
        try {
            // Validar ID
            if (id <= 0) {
                throw new Exception("ID de categoria inválido");
            }
            
            // Llamar al DAO para eliminar
            categoriaDAO.eliminar(id);
            
        } catch (Exception e) {
            throw new Exception("Error al eliminar categoria: " + e.getMessage(), e);
        }
    }

    @Override
    public Categoria buscarCategoria(int id) throws Exception {
        try {
            // Validar ID
            if (id <= 0) {
                throw new Exception("ID de categoria inválido");
            }
            
            // Llamar al DAO para buscar
            return categoriaDAO.obtener(id);
            
        } catch (Exception e) {
            throw new Exception("Error al buscar categoria: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Categoria> listarCategorias() throws Exception {
        try {
                // Llamar al DAO para listar todos
                return categoriaDAO.listarTodos();

            } catch (Exception e) {
                throw new Exception("Error al listar categorias: " + e.getMessage(), e);
            }
        }

    // Método auxiliar para validaciones
    private void validarCategoria(Categoria categoria) throws Exception {
        if (categoria.getId()< 0) {
            throw new Exception("La categoria debe estar entre 1 y 100");
        }
    }
    
}
