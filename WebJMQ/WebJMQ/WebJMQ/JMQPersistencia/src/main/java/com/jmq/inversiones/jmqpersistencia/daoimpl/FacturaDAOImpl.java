package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.pagos.Factura;
import com.jmq.inversiones.jmqpersistencia.dao.FacturaDAO;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;


public class FacturaDAOImpl extends BaseDAOImpl<Factura> implements FacturaDAO{

    @Override
    protected String getInsertQuery() {
        return "{CALL FACTURA_INSERTAR(?,?,?,?,?)}";
    }

    @Override
    protected String getUpdateQuery() {
        return "{CALL FACTURA_MODIFICAR(?,?,?,?,?,?,?,?,?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "{CALL FACTURA_ELIMINAR(?)}";
    }

    @Override
    protected String getSelectByIdQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getSelectAllQuery() {
        return "{CALL FACTURA_LISTAR()}";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, Factura entity) throws SQLException {
        ps.setInt(1,entity.getId());
        
        
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, Factura entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected Factura createFromResultSet(ResultSet rs) throws SQLException {
        Factura f = new Factura();
        
        f.setId(rs.getInt("idFactura"));
        f.setRUC(rs.getString("RUC"));
        f.setRazon_social(rs.getString("razon_social"));
        f.setDireccion(rs.getString("direccion"));
        f.setFecha_emision(rs.getDate("fecha_emision"));
        f.setMonto_total(rs.getDouble("monto_total"));
        f.setDireccion(rs.getString("direccion_fiscal"));
        return f;
    }

    @Override
    protected void setId(Factura entity, Integer id) {
        entity.setId(id);
    }
    
}
