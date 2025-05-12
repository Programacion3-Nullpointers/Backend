package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.ventas.Detalle;
import com.jmq.inversiones.dominio.ventas.Producto;
import com.jmq.inversiones.jmqpersistencia.dao.DetalleDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DetalleDAOImpl extends BaseDAOImpl<Detalle> implements DetalleDAO{

    private final ProductoDAOImpl producto;
    
    public DetalleDAOImpl(){
        this.producto = new ProductoDAOImpl();
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL DETALLE_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Detalle entity) throws SQLException {
        ps.setInt(1,entity.getId());
        ps.setInt(2,entity.getProducto().getId());
        ps.setInt(3,entity.getCantidad());
        ps.setDouble(4,entity.getPrecio_unitario());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Detalle entity) throws SQLException {
        ps.setInt(1, entity.getId()); // id_orden
        ps.setInt(2, entity.getProducto().getId()); // id_producto
        ps.setInt(3, entity.getCantidad());
        ps.setDouble(4, entity.getPrecio_unitario());
    }


    @Override
    protected Detalle createFromResultSet(ResultSet rs) throws SQLException {
        Detalle detalle = new Detalle();
        detalle.setId(rs.getInt("idDetalle"));
        detalle.setProducto(this.producto.obtener(rs.getInt("id_producto")));
        detalle.setPrecio_unitario(rs.getDouble("precio_unitario"));
        detalle.setCantidad(rs.getInt("cantidad"));
        return detalle;
    }

    @Override
    protected void setId(Detalle entity, Integer id) {
        entity.setId(id);
    }
    
}
