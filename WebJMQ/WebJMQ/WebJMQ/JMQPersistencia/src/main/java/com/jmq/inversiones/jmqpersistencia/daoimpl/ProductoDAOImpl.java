package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoDAO;

import java.sql.*;

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
        return  "DELETE FROM Producto WHERE idProducto = ?";
    }

    @Override
    protected String getSelectByIdQuery() {
        // Asumiendo que implementas este SP, si no, deberías removerlo o usar SELECT directo
        return "{CALL PRODUCTO_OBTENER(?)}";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM Producto";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Producto entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setString(2, entity.getNombre());
        cs.setString(3, entity.getDescripcion());
        cs.setInt(4, entity.getStock());
        cs.setDouble(5, entity.getPrecio());
        cs.setBytes(6, entity.getImagen());
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
        cs.setBytes(6, entity.getImagen());
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
        producto.setImagen(rs.getBytes("Imagen"));
        producto.setActivo(rs.getBoolean("activo"));
        producto.setCategoria(categoriaDAO.obtener(rs.getInt("idCategoria")));
        return producto;
    }

    @Override
    protected void setId(Producto entity, Integer id) {
        entity.setId(id);
    }

    @Override
    public void descontarStock(int id, int stock) {
        String query = "UPDATE Producto SET stock = stock - ? WHERE idProducto = ?";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, stock);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al descontar stock", e);
        }
    }
}
