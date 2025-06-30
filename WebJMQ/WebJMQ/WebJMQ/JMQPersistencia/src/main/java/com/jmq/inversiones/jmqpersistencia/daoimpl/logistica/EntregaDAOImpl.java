package com.jmq.inversiones.jmqpersistencia.daoimpl.logistica;

import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.logistica.Entrega;
import com.jmq.inversiones.dominio.logistica.TipoEntrega;
import com.jmq.inversiones.jmqpersistencia.dao.logistica.EntregaDAO;
import com.jmq.inversiones.dbmanager.DBManager;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class EntregaDAOImpl extends BaseDAOImpl<Entrega> implements EntregaDAO{

    @Override
    protected String getInsertQuery() {
        return "{CALL ENTREGA_INSERTAR(?, ?, ?, ?, ?, ?)}";// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL ENTREGA_MODIFICAR(?, ?, ?, ?, ?, ?)}";// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL ENTREGA_ELIMINAR(?)}"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    
    @Override
    protected String getSelectAllQuery() {
        return "{CALL ENTREGA_LISTAR()}"; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Entrega entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.registerOutParameter(1, Types.INTEGER); // OUT _id_entrega
        cs.setInt(2, entity.getOrden().getId());
        cs.setTimestamp(3,new Timestamp(entity.getFecha_entrega().getTime()));
        cs.setString(4, entity.getDireccion());
        cs.setString(5, entity.getDniRecibo());
        cs.setString(6, entity.getTipoEntrega().name());
    }
    
    @Override
    protected void setUpdateParameters(PreparedStatement ps, Entrega entity) throws SQLException {
    CallableStatement cs = (CallableStatement) ps;
    cs.setInt(1, entity.getId());
    cs.setInt(2, entity.getOrden().getId());
    cs.setTimestamp(3, new Timestamp(entity.getFecha_entrega().getTime()));
    cs.setString(4, entity.getDireccion());
    cs.setString(5, entity.getDniRecibo());
    cs.setString(6, entity.getTipoEntrega().name());

        
    }

    @Override
    protected Entrega createFromResultSet(ResultSet rs) throws SQLException {
        Entrega entre = new Entrega();
        entre.setId(rs.getInt("idEntrega"));
        OrdenVenta orden = new OrdenVenta();
        orden.setId(rs.getInt("id_orden"));
        entre.setOrden(orden);
        
        entre.setDniRecibo(rs.getString("dni"));
        entre.setDireccion(rs.getString("direccion"));
        entre.setFecha_entrega(rs.getTimestamp("fecha_entrega"));
        entre.setTipoEntrega(TipoEntrega.valueOf(rs.getString("TipoEntrega")));
        return entre;
    }

    @Override
    protected void setId(Entrega entity, Integer id) {
        entity.setId(id);
    }

    @Override
    protected String getSelectByIdQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
