package com.jmq.inversiones.jmqpersistencia.daoimpl.ventas;

import com.jmq.inversiones.jmqpersistencia.daoimpl.ventas.CategoriaDAOImpl;
import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.ventas.Categoria;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.jmqpersistencia.dao.ventas.ProductoDAO;

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

    @Override
    public List<Producto> filtrarProductos(String nombreCategoria, Boolean activo, 
            Double precioMin, Double precioMax, 
            Integer stockMin, Integer stockMax,Boolean conDescuento) throws SQLException {
        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT p.*, c.nombre AS nombre_categoria, c.descripcion AS descripcion_categoria " +
                 "FROM Producto p " +
                 "JOIN Categoria c ON p.idCategoria = c.idCategoria " +
                 "LEFT JOIN Descuento d ON c.idDescuento = d.idDescuento " +
                 "WHERE 1 = 1";

        List<Object> params = new ArrayList<>();

        if (nombreCategoria != null && !nombreCategoria.isEmpty()) {
            sql += " AND c.nombre = ?";
            params.add(nombreCategoria);
        }
        if (activo != null) {
            sql += " AND p.activo = ?";
            params.add(activo ? 1 : 0);
        }
        if (precioMin != null) {
            sql += " AND p.precio >= ?";
            params.add(precioMin);
        }
        if (precioMax != null) {
            sql += " AND p.precio <= ?";
            params.add(precioMax);
        }
        if (stockMin != null) {
            sql += " AND p.stock >= ?";
            params.add(stockMin);
        }
        if (stockMax != null) {
            sql += " AND p.stock <= ?";
            params.add(stockMax);
        }
        if (conDescuento != null) {
            if (conDescuento) {
                sql += " AND c.idDescuento IS NOT NULL AND d.activo = 1";
            } else {
                sql += " AND (c.idDescuento IS NULL OR d.activo = 0)";
            }
        }
         
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = createFromResultSet(rs);
             
                productos.add(p);
            }
        }
         return productos;
    }
}
