package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoCotizacionDAO;
import com.jmq.inversiones.dbmanager.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoCotizacionDAOImpl extends BaseDAOImpl<ProductoCotizacion> implements ProductoCotizacionDAO {

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO productoCotizado (idproductoCotizado, descripcion, cantidad, precioCotizado, idCotizacion) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE productoCotizado SET descripcion=?, cantidad=?, precioCotizado=? WHERE idproductoCotizado=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM productoCotizado WHERE idCotizacion=?";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM productoCotizado WHERE idproductoCotizado = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM productoCotizado";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        ps.setInt(1, entity.getId());
        ps.setString(2, entity.getDescripcion());
        ps.setInt(3, entity.getCantidad());
        ps.setDouble(4, entity.getPrecioCotizado());
        ps.setInt(5, this.idCotizacionAux); // necesario para relacionar con cotización
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        ps.setString(1, entity.getDescripcion());
        ps.setInt(2, entity.getCantidad());
        ps.setDouble(3, entity.getPrecioCotizado());
        ps.setInt(4, entity.getId());
    }

    @Override
    protected ProductoCotizacion createFromResultSet(ResultSet rs) throws SQLException {
        ProductoCotizacion prod = new ProductoCotizacion();
        prod.setId(rs.getInt("idproductoCotizado"));
        prod.setCantidad(rs.getInt("cantidad"));
        prod.setDescripcion(rs.getString("descripcion"));
        prod.setPrecioCotizado(rs.getDouble("precioCotizado"));
        return prod;
    }

    @Override
    protected void setId(ProductoCotizacion entity, Integer id) {
        entity.setId(id);
    }

    // === Métodos personalizados ===

    private int idCotizacionAux;

    public void agregar(ProductoCotizacion pc, int idCotizacion, Connection conn) {
        this.idCotizacionAux = idCotizacion;
        try (PreparedStatement ps = conn.prepareStatement(getInsertQuery())) {
            setInsertParameters(ps, pc);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar producto cotizado", e);
        }
    }

    public void agregar(ProductoCotizacion pc, int idCotizacion) {
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            agregar(pc, idCotizacion, conn);
        } catch (SQLException e) {
            throw new RuntimeException("Error al abrir conexión para insertar producto cotizado", e);
        }
    }

    public void eliminarPorCotizacion(int idCotizacion) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(getDeleteQuery())) {
            ps.setInt(1, idCotizacion);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar productos cotizados por cotización", e);
        }
    }

    public List<ProductoCotizacion> obtenerPorCotizacion(int idCotizacion) {
        List<ProductoCotizacion> lista = new ArrayList<>();
        String query = "SELECT * FROM productoCotizado WHERE idCotizacion = ?";
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idCotizacion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(createFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener productos cotizados por cotización", e);
        }
        return lista;
    }
}
