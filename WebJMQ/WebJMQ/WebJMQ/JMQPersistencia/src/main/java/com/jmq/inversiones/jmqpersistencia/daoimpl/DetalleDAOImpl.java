package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.ventas.Detalle;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.DetalleDAO;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;


public class DetalleDAOImpl extends BaseDAOImpl<Detalle> implements DetalleDAO{

    private final ProductoDAOImpl producto;
    private final OrdenVentaDAOImpl orden;
    
    public DetalleDAOImpl(){
        this.producto = new ProductoDAOImpl();
        this.orden = new OrdenVentaDAOImpl();
    }
    @Override
    protected String getInsertQuery() {
        return "{CALL DETALLE_INSERTAR(?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL DETALLE_MODIFICAR(?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL DETALLE_ELIMINAR(?, ?)}";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT * FROM detalle WHERE id_orden = ? AND id_producto = ?";    
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL DETALLE_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Detalle entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.setInt(1, entity.getOrden().getId()); // idOrdenVenta
        cs.setInt(2, entity.getProducto().getId());   // idProducto
        cs.setInt(3, entity.getCantidad());
        cs.setDouble(4, entity.getPrecio_unitario());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Detalle entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.setInt(1, entity.getOrden().getId()); // id_orden
        cs.setInt(2, entity.getProducto().getId()); // id_producto
        cs.setInt(3, entity.getCantidad());
        cs.setDouble(4, entity.getPrecio_unitario());
    }


    @Override
    protected Detalle createFromResultSet(ResultSet rs) throws SQLException {
        Detalle detalle = new Detalle();
        detalle.setOrden(this.orden.obtener(rs.getInt("id_orden")));
        detalle.setProducto(this.producto.obtener(rs.getInt("id_producto")));
        detalle.setPrecio_unitario(rs.getDouble("precio_unitario"));
        detalle.setCantidad(rs.getInt("cantidad"));
        return detalle;
    }
    
    protected Detalle createFromResultSet2(ResultSet rs) throws SQLException {
        Detalle detalle = new Detalle();
        detalle.setProducto(this.producto.obtener(rs.getInt("id_producto")));
        detalle.setPrecio_unitario(rs.getDouble("precio_unitario"));
        detalle.setCantidad(rs.getInt("cantidad"));
        return detalle;
    }

//    @Override
//    protected void setId(Detalle entity, Integer id) {
//        //entity.setId(id);
//    }
    @Override
    public Detalle obtener(int idOrden, int idProducto) {
    String query = "SELECT * FROM Detalle WHERE id_orden = ? AND id_producto = ?";
    try (var conn = DBManager.getInstance().obtenerConexion();
         var ps = conn.prepareStatement(query)) {
        ps.setInt(1, idOrden);
        ps.setInt(2, idProducto);
        try (var rs = ps.executeQuery()) {
            if (rs.next()) {
                return createFromResultSet(rs);
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error al obtener detalle", e);
    }
    return null;
    }
    
    @Override
    public void agregar(Detalle entity) {
        try (var conn = DBManager.getInstance().obtenerConexion();
             var cs = conn.prepareCall("{CALL DETALLE_INSERTAR(?, ?, ?, ?)}")) {

            cs.setInt(1, entity.getOrden().getId());
            cs.setInt(2, entity.getProducto().getId());
            cs.setInt(3, entity.getCantidad());
            cs.setDouble(4, entity.getPrecio_unitario());

            cs.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar detalle", e);
        }
    }

    @Override
    public void actualizar(Detalle entity) {
        try (var conn = DBManager.getInstance().obtenerConexion();
             var cs = conn.prepareCall("{CALL DETALLE_MODIFICAR(?, ?, ?, ?)}")) {

            cs.setInt(1, entity.getOrden().getId());
            cs.setInt(2, entity.getProducto().getId());
            cs.setInt(3, entity.getCantidad());
            cs.setDouble(4, entity.getPrecio_unitario());

            cs.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar detalle", e);
        }
    }

    @Override
    public void eliminar(int idOrden, int idProducto) {
        try (var conn = DBManager.getInstance().obtenerConexion();
             var cs = conn.prepareCall("{CALL DETALLE_ELIMINAR(?, ?)}")) {

            cs.setInt(1, idOrden);
            cs.setInt(2, idProducto);
            cs.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar detalle", e);
        }
    }

    @Override
    protected void setId(Detalle entity, Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    public List<Detalle> listarPorOrden(int idOrden){
        List<Detalle> detalles = new ArrayList<>();

        String query = "{CALL DETALLE_LISTAR_POR_ORDEN(?)}";

        try (Connection conn = DBManager.getInstance().obtenerConexion();
             CallableStatement cs = conn.prepareCall(query)) {

            cs.setInt(1, idOrden);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                detalles.add(createFromResultSet(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar detalles por orden", e);
        }

        return detalles;
    }
}

