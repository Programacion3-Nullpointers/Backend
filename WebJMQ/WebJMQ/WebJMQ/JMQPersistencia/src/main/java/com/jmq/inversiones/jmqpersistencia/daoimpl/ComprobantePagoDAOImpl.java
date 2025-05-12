package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.pagos.ComprobantePago;
import com.jmq.inversiones.dominio.pagos.MetodoPago;
import com.jmq.inversiones.jmqpersistencia.dao.ComprobantePagoDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ComprobantePagoDAOImpl extends BaseDAOImpl<ComprobantePago> implements ComprobantePagoDAO{

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO ComprobantePago(idOrdenVenta, metodo_pago, fecha_pago, monto_total) VALUES (?,?,?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getDeleteQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getSelectByIdQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String getSelectAllQuery() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, ComprobantePago entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, ComprobantePago entity) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected ComprobantePago createFromResultSet(ResultSet rs) throws SQLException {
        ComprobantePago compro = new ComprobantePago();
        compro.setId(rs.getInt("idComprobantePago"));
        compro.setFecha_pago(rs.getDate("fecha_pago"));
        compro.setMetodoPago(MetodoPago.valueOf(rs.getString("metodo_pago")));
        compro.setMonto_total(rs.getDouble("monto_total"));
        return compro;
    }

    @Override
    protected void setId(ComprobantePago entity, Integer id) {
        entity.setId(id);
        
    }
    
}
