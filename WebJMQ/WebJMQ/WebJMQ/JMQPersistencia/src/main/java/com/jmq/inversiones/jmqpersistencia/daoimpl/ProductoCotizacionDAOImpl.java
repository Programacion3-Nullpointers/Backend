package com.jmq.inversiones.jmqpersistencia.daoimpl;

<<<<<<< HEAD
import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.contizaciones.Cotizacion;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
=======
>>>>>>> ramaDaysi
import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoCotizacionDAO;
<<<<<<< HEAD
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
=======
import com.jmq.inversiones.dbmanager.DBManager;

import java.sql.*;
>>>>>>> ramaDaysi
import java.util.ArrayList;
import java.util.List;

public class ProductoCotizacionDAOImpl extends BaseDAOImpl<ProductoCotizacion> implements ProductoCotizacionDAO {

    
    
    @Override
    protected String getInsertQuery() {
<<<<<<< HEAD
        return "INSERT INTO Cotizacion (idproductoCotizado,descripcion,cantidad,"
                + "precioCotizado,idCotizacion) VALUES ( ?, ?, ?, ?, ? )";
=======
        return "INSERT INTO productoCotizado (idproductoCotizado, descripcion, cantidad, precioCotizado, idCotizacion) VALUES (?, ?, ?, ?, ?)";
>>>>>>> ramaDaysi
    }

    @Override
    protected String getUpdateQuery() {
<<<<<<< HEAD
        return "UPDATE productoCotizado SET precioCotizado = ?"
                + " WHERE idproductoCotizado = ? AND idCotizacion = ?";
=======
        return "UPDATE productoCotizado SET descripcion=?, cantidad=?, precioCotizado=? WHERE idproductoCotizado=?";
>>>>>>> ramaDaysi
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
<<<<<<< HEAD
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        //return "SELECT * FROM productoCotizado WHERE idCotizacion = ?";
=======
        return "SELECT * FROM productoCotizado";
>>>>>>> ramaDaysi
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        ps.setInt(1, entity.getId());
        ps.setString(2, entity.getDescripcion());
        ps.setInt(3, entity.getCantidad());
        ps.setDouble(4, entity.getPrecioCotizado());
<<<<<<< HEAD
        ps.setInt(5, entity.getFid_cotizacion());
=======
        ps.setInt(5, this.idCotizacionAux); // necesario para relacionar con cotización
>>>>>>> ramaDaysi
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
<<<<<<< HEAD
        ps.setDouble(1, entity.getPrecioCotizado());
        ps.setInt(2, entity.getId());
        ps.setInt(3, entity.getFid_cotizacion());
=======
        ps.setString(1, entity.getDescripcion());
        ps.setInt(2, entity.getCantidad());
        ps.setDouble(3, entity.getPrecioCotizado());
        ps.setInt(4, entity.getId());
>>>>>>> ramaDaysi
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

<<<<<<< HEAD
    
    @Override
    public void actualizarPrecioCotizacion(Integer id, Integer fid,double precio) {
        ProductoCotizacion pro = new ProductoCotizacion();
        pro.setId(id);
        pro.setFid_cotizacion(fid);
        pro.setPrecioCotizado(precio);
        actualizar(pro);
    }

    
    
    
=======
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
>>>>>>> ramaDaysi
}
