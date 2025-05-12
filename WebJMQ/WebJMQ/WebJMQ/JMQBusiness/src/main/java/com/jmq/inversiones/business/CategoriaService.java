package com.jmq.inversiones.business;

import com.jmq.inversiones.dominio.ventas.Categoria;
import java.util.List;

public interface CategoriaService {
    void registrarCategoria(Categoria categoria) throws Exception;
    void actualizarCategoria(Categoria categoria) throws Exception;
    void eliminarCategoria(int id) throws Exception;
    Categoria buscarCategoria(int id) throws Exception;
    List<Categoria> listarCategorias() throws Exception;

}
