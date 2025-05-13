package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl extends BaseDAOImpl<Producto> implements ProductoDAO {

    private final CategoriaDAOImpl categoriaDAO;

    public ProductoDAOImpl() {
        this.categoriaDAO = new CategoriaDAOImpl();
    }

    @Override
    protected String getInsertQuery() {
        return "{CALL PRODUCTO_INSERTAR(?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL PRODUCTO_MODIFICAR(?, ?, ?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL PRODUCTO_ELIMINAR(?)}";
    }

    @Override
    protected String getSelectByIdQuery() {
        // Asumiendo que implementas un SP para obtener por ID en el futuro
        throw new UnsupportedOperationException("No implementado: crear SP PRODUCTO_OBTENER");
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL PRODUCTO_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Producto entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.registerOutParameter(1, Types.INTEGER); // OUT _id_producto
        cs.setString(2, entity.getNombre());
        cs.setString(3, entity.getDescripcion());
        cs.setInt(4, entity.getStock());
        cs.setDouble(5, entity.getPrecio());
        cs.setString(6, entity.getImagen());
        cs.setBoolean(7, entity.isActivo());
        cs.setInt(8, entity.getCategoria().getId());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Producto entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.setInt(1, entity.getId());
        cs.setString(2, entity.getNombre());
        cs.setString(3, entity.getDescripcion());
        cs.setInt(4, entity.getStock());
        cs.setDouble(5, entity.getPrecio());
        cs.setString(6, entity.getImagen());
        cs.setBoolean(7, entity.isActivo());
        cs.setInt(8, entity.getCategoria().getId());
    }

    @Override
    protected Producto createFromResultSet(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("idProducto"));
        producto.setNombre(rs.getString("nombre"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setStock(rs.getInt("stock"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setImagen(rs.getString("Imagen"));
        producto.setActivo(rs.getBoolean("activo"));
        producto.setCategoria(categoriaDAO.obtener(rs.getInt("idCategoria")));
        return producto;
    }

    @Override
    protected void setId(Producto entity, Integer id) {
        entity.setId(id);
    }

   
}
