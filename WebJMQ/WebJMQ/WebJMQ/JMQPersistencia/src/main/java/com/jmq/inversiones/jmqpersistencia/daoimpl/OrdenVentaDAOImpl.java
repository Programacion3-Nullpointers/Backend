package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.ventas.EstadoCompra;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.OrdenVentaDAO;
import com.jmq.inversiones.jmqpersistencia.dao.UsuarioDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class OrdenVentaDAOImpl extends BaseDAOImpl<OrdenVenta> implements OrdenVentaDAO{

    private final UsuarioDAOImpl usuario;
    
    public OrdenVentaDAOImpl(){
        usuario = new UsuarioDAOImpl();
    }
    @Override
    protected String getInsertQuery() {
        return "{CALL ORDENVENTA_INSERTAR( ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL ORDENVENTA_MODIFICAR( ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL ORDENVENTA_ELIMINAR( ? )}";
    }

    @Override
    protected String getSelectByIdQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL ORDENVENTA_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, OrdenVenta orden) throws SQLException {
        ps.setString(1, orden.getEstado_compra().name());
        ps.setDate(2, new java.sql.Date(orden.getFecha_orden().getTime()));
        ps.setBoolean(3, orden.isActivo());
        ps.setInt(4, orden.getUsuario().getId());
    }


    @Override
    protected void setUpdateParameters(PreparedStatement ps, OrdenVenta orden) throws SQLException {
        ps.setInt(1, orden.getId());
        ps.setString(2, orden.getEstado_compra().name());
        ps.setDate(3, new java.sql.Date(orden.getFecha_orden().getTime()));
        ps.setBoolean(4, orden.isActivo());
        ps.setInt(5, orden.getUsuario().getId());
    }

    @Override
    protected OrdenVenta createFromResultSet(ResultSet rs) throws SQLException {
        OrdenVenta venta = new OrdenVenta();
        
        venta.setId(rs.getInt("idOrdenVenta"));
        venta.setUsuario(usuario.obtener(rs.getInt("idUsuario")));
        venta.setFecha_orden(rs.getDate("fecha_orden"));
        venta.setActivo(rs.getBoolean("activo"));
        venta.setEstado_compra(EstadoCompra.valueOf(rs.getString("estado_compra")));

        return venta;
    }

    @Override
    protected void setId(OrdenVenta entity, Integer id) {
        entity.setId(id);
    }
    
    @Override
    public void agregar(OrdenVenta orden) {
        // Sobrescribimos el método para usar el SP
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                // Registrar la venta
                try (CallableStatement cs = conn.prepareCall(getInsertQuery())) {
                    
                    cs.registerOutParameter(1, Types.INTEGER);
                    cs.setString(2, orden.getEstado_compra().name());
                    cs.setDate(3, (Date) orden.getFecha_orden());
                    cs.setBoolean(4, orden.isActivo());
                    cs.setInt(5, orden.getUsuario().getId());
                    cs.execute();
                    setId(orden, cs.getInt(1));
                }
                
                
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar descuento", e);
        }
    }
    
    @Override
    public void actualizar(OrdenVenta orden) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getUpdateQuery())) {
            
            cs.setInt(1, orden.getId());
            cs.setString(2, orden.getEstado_compra().name());
            cs.setDate(3, (Date) orden.getFecha_orden());
            cs.setBoolean(4, orden.isActivo());
            cs.setInt(5, orden.getUsuario().getId());
            
            cs.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar descuento", e);
        }
    }
    
    @Override
    public void eliminar(Integer id) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getDeleteQuery())) {

            cs.setInt(1, id);
            cs.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar orden", e);
        }
    }

    @Override
    public List<OrdenVenta> listarTodos() {
        List<OrdenVenta> ordenes = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getSelectAllQuery());
             ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                ordenes.add(createFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar descuentos", e);
        }
        return ordenes;
    }
    
}
