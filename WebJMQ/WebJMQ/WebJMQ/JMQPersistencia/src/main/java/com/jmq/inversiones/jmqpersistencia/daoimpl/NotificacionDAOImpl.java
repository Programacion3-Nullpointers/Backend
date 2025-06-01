package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.notificaciones.Notificacion;
import com.jmq.inversiones.jmqpersistencia.dao.NotificacionDAO;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;


public class NotificacionDAOImpl extends BaseDAOImpl<Notificacion> implements NotificacionDAO {

    @Override
    protected String getInsertQuery() {
        return "{CALL NOTIFICACION_INSERTAR(?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL NOTIFICACION_MODIFICAR(?, ?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL NOTIFICACION_ELIMINAR(?)}";
    }

    @Override
    protected String getSelectByIdQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL NOTIFICACION_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Notificacion entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setString(2, entity.getTipo());
        cs.setString(3, entity.getMensaje());
        cs.setTimestamp(4, new Timestamp(entity.getFecha_envio().getTime()));
        cs.setString(5, entity.getEstado());
        cs.setString(6, entity.getAsunto());
        
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Notificacion entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.setInt(1, entity.getId());
        cs.setString(2, entity.getTipo());
        cs.setString(3, entity.getMensaje());
        cs.setTimestamp(4,new Timestamp(entity.getFecha_envio().getTime()));
        cs.setString(5, entity.getEstado());
        cs.setString(6, entity.getAsunto());
    }

    @Override
    protected Notificacion createFromResultSet(ResultSet rs) throws SQLException {
        Notificacion noti = new Notificacion();
        noti.setId(rs.getInt("idNotificacion"));
        noti.setMensaje(rs.getString("mensaje"));
        noti.setTipo(rs.getString("tipo"));
        
        Timestamp ts = rs.getTimestamp("fecha_envio");
        if(ts!=null){
            noti.setFecha_envio(new Date(ts.getTime()));
        }
        
        noti.setEstado(rs.getString("estado"));
        return noti;
    }

    @Override
    protected void setId(Notificacion entity, Integer id) {
        entity.setId(id);
    }
    
}
