package com.jmq.inversiones.jmqpersistencia.daoimpl.ventas;

import com.jmq.inversiones.jmqpersistencia.daoimpl.usuario.UsuarioDAOImpl;
import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.ventas.EstadoCompra;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.jmqpersistencia.dao.ventas.OrdenVentaDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        return "SELECT * FROM OrdenVenta WHERE idOrdenVenta = ? AND activo = 1"; 
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL ORDENVENTA_LISTAR()}";
    }
    
    protected String getOrdenByIdUsuario(){
        return "{CALL ORDEN_VENTA_LISTAR_POR_USUARIO(?)}";
    }
    @Override
    protected void setInsertParameters(PreparedStatement ps, OrdenVenta orden) throws SQLException {
        CallableStatement cs= (CallableStatement) ps;
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setString(2, orden.getEstado_compra().name());
        cs.setTimestamp(3,new Timestamp(orden.getFecha_orden().getTime()));
        cs.setBoolean(4, orden.isActivo());
        cs.setInt(5, orden.getUsuario().getId());
        
    }


    @Override
    protected void setUpdateParameters(PreparedStatement ps, OrdenVenta orden) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.setInt(1, orden.getId());
        cs.setString(2, orden.getEstado_compra().name());
        cs.setTimestamp(3,new Timestamp(orden.getFecha_orden().getTime()));
        cs.setBoolean(4, orden.isActivo());
        cs.setInt(5, orden.getUsuario().getId());
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
        try (Connection conn = DBManager.getInstance().obtenerConexion()) {
            conn.setAutoCommit(false);
            try {
                // Registrar la venta
                try (CallableStatement cs = conn.prepareCall(getInsertQuery())) {
                    
                    cs.registerOutParameter(1, Types.INTEGER);
                    cs.setString(2, orden.getEstado_compra().name());
                    cs.setTimestamp(3, new Timestamp(orden.getFecha_orden().getTime()));
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
            throw new RuntimeException("Error al registrar orden", e);
        }
    }
    
    @Override
    public void actualizar(OrdenVenta orden) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getUpdateQuery())) {
            
            cs.setInt(1, orden.getId());
            cs.setString(2, orden.getEstado_compra().name());
            cs.setTimestamp(3, new Timestamp(orden.getFecha_orden().getTime()));
            cs.setBoolean(4, orden.isActivo());
            cs.setInt(5, orden.getUsuario().getId());
            
            cs.execute();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar orden", e);
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
    @Override
    public List<OrdenVenta> listarPorUsuario(int idUsuario) {
        List<OrdenVenta> lista = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(getOrdenByIdUsuario())) {
            cs.setInt(1, idUsuario);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    OrdenVenta orden = new OrdenVenta();
                    orden.setId(rs.getInt("idOrdenVenta"));
                    orden.setEstado_compra(EstadoCompra.valueOf(rs.getString("estado_compra")));
                    orden.setFecha_orden(rs.getTimestamp("fecha_orden"));
                    orden.setActivo(rs.getBoolean("activo"));
                    // Puedes setear también el usuario si tienes un DAO o DTO relacionado
                    // orden.setUsuario(usuarioDAO.obtener(rs.getInt("idUsuario")));
                    lista.add(orden);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar órdenes por usuario", e);
        }

        return lista;
    }
    @Override
    public List<OrdenVenta> filtrarOrdenesVenta(String estadoCompra, Boolean activo, Integer idUsuario,
                                            String fechaDesde, String fechaHasta) throws SQLException {
        List<OrdenVenta> ordenes = new ArrayList<>();

        String sql = "SELECT * FROM OrdenVenta WHERE 1 = 1";
        List<Object> params = new ArrayList<>();

        if (estadoCompra != null && !estadoCompra.isEmpty()) {
            sql += " AND estado_compra = ?";
            params.add(estadoCompra);
        }

        if (activo != null) {
            sql += " AND activo = ?";
            params.add(activo);
        }

        if (idUsuario != null && idUsuario != 0) {
            sql += " AND idUsuario = ?";
            params.add(idUsuario);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (fechaDesde != null && !fechaDesde.isEmpty()) {
            sql += " AND fecha_orden >= ?";
            params.add(Date.valueOf(LocalDate.parse(fechaDesde, formatter)));
        }

        if (fechaHasta != null && !fechaHasta.isEmpty()) {
            sql += " AND fecha_orden <= ?";
            params.add(Date.valueOf(LocalDate.parse(fechaHasta, formatter)));
        }


        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrdenVenta ov = createFromResultSet(rs);
                ordenes.add(ov);
            }
        }

        return ordenes;
    }

}
