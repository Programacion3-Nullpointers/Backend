package com.jmq.inversiones.jmqpersistencia.daoimpl;




import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.contizaciones.ProductoCotizacion;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.jmqpersistencia.dao.ProductoCotizacionDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class ProductoCotizacionDAOImpl extends BaseDAOImpl<ProductoCotizacion> implements ProductoCotizacionDAO {

    
    
    @Override
    protected String getInsertQuery() {
        return "{CALL PRODUCTOCOTIZACION_INSERTAR(?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL PRODUCTOCOTIZACION_MODIFICAR(?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL PRODUCTOCOTIZACION_ELIMINAR(?)}";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "{CALL PRODUCTOCOTIZACION_OBTENER(?)}";
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL PRODUCTOCOTIZACION_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.registerOutParameter(1, Types.INTEGER); // OUT _id
        cs.setString(2, entity.getDescripcion());
        cs.setInt(3, entity.getCantidad());
        cs.setDouble(4, entity.getPrecioCotizado());
        cs.setInt(5, entity.getFid_cotizacion());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, ProductoCotizacion entity) throws SQLException {
        ps.setString(1, entity.getDescripcion());
        ps.setInt(2, entity.getCantidad());
        ps.setDouble(3, entity.getPrecioCotizado());
        ps.setInt(4, entity.getFid_cotizacion());
        ps.setInt(5, entity.getId());
    }

    @Override
    protected ProductoCotizacion createFromResultSet(ResultSet rs) throws SQLException {
        ProductoCotizacion prod = new ProductoCotizacion();
        prod.setId(rs.getInt("idproductoCotizado"));
        prod.setCantidad(rs.getInt("cantidad"));
        prod.setDescripcion(rs.getString("descripcion"));
        prod.setPrecioCotizado(rs.getDouble("precioCotizado"));
        prod.setFid_cotizacion(rs.getInt("idCotizacion"));
        return prod;
    }

    @Override
    protected void setId(ProductoCotizacion entity, Integer id) {
        entity.setId(id);
    }
    
//    public void actualizarPrecioCotizacion(Integer id, Integer fid,double precio) {
//        ProductoCotizacion existente = obtenerPorIdYCotizacion(id,fid); // Trae el actual de la BD
//        if (existente != null) {
//            existente.setPrecioCotizado(precio);
//            actualizar(existente); 
//        } else if (existente.getDescripcion()== null || existente.getDescripcion().isBlank()) {
//            throw new IllegalStateException("La descripción no puede ser nula al actualizar");
//        }else {
//            throw new RuntimeException("ProductoCotizacion no encontrado para ID: " + id+ " y Cotización: " + fid);
//        }
//    }
//    public void agregar(ProductoCotizacion pc, int idCotizacion) {
//        pc.setFid_cotizacion(idCotizacion);
//        super.agregar(pc);
//    }

//    public List<ProductoCotizacion> obtenerPorCotizacion(int idCotizacion) {
//        List<ProductoCotizacion> lista = new ArrayList<>();
//        String sql = "SELECT * FROM productoCotizado WHERE idCotizacion = ?";
//
//        try (Connection conn = DBManager.getInstance().obtenerConexion();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, idCotizacion);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    lista.add(createFromResultSet(rs));
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error al obtener productos por cotización", e);
//        }
//        return lista;
//    }
//
//    public ProductoCotizacion obtenerPorIdYCotizacion(int idProducto, int idCotizacion) {
//        String sql = "SELECT * FROM productoCotizado WHERE idproductoCotizado = ? AND idCotizacion = ?";
//
//        try (Connection conn = DBManager.getInstance().obtenerConexion();
//             PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, idProducto);
//            ps.setInt(2, idCotizacion);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    return createFromResultSet(rs);
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error al obtener producto por ID y cotización", e);
//        }
//
//        return null;
//    }

    
//    public void eliminar(int idCotizacion) {
//        try (Connection conn = DBManager.getInstance().obtenerConexion();
//             PreparedStatement ps = conn.prepareStatement(getDeleteQuery())) {
//
//            ps.setInt(1, idCotizacion);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error al eliminar productos cotizados", e);
//        }
//    }

    @Override
    public void actualizarPrecioCotizacion(ProductoCotizacion pro, double precio) {
        pro.setPrecioCotizado(precio);
        actualizar(pro);
    }

    @Override
    public void listarPorUsuario(int idUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
