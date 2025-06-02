package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.pagos.ComprobantePago;
import com.jmq.inversiones.dominio.pagos.MetodoPago;
import com.jmq.inversiones.dominio.ventas.OrdenVenta;
import com.jmq.inversiones.jmqpersistencia.dao.ComprobantePagoDAO;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;


public abstract class ComprobantePagoDAOImpl extends BaseDAOImpl<ComprobantePago> implements ComprobantePagoDAO{

    private final OrdenVentaDAOImpl ordenVentaDAO = new OrdenVentaDAOImpl();

    @Override
    protected String getInsertQuery() {
         return "{CALL COMPROBANTE_PAGO_INSERTAR(?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getUpdateQuery() {
       return "{CALL COMPROBANTE_PAGO_ACTUALIZAR(?, ?, ?, ?, ?)}";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM ComprobantePago WHERE idComprobantePago = ?";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "{CALL GetComprobantePagoById(?)}";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELCT * FROM ComprobantePago";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, ComprobantePago entity) throws SQLException {
        CallableStatement cs = (CallableStatement) ps;
        cs.registerOutParameter(1, Types.INTEGER);
        cs.setInt(2,entity.getOrden().getId());
        cs.setString(3, entity.getMetodoPago().name()); //usamos el nombre del enum
        cs.setTimestamp(4, new Timestamp(entity.getFecha_pago().getTime()));
        cs.setDouble(5, entity.getMonto_total());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, ComprobantePago entity) throws SQLException {
        ps.setInt(1, entity.getOrden().getId());
        ps.setString(2, entity.getMetodoPago().name()); // Enum como String
        ps.setTimestamp(3, new Timestamp(entity.getFecha_pago().getTime()));
        ps.setDouble(4, entity.getMonto_total());
        ps.setInt(5, entity.getId());
    }

    protected void  fillFromResultSetBase(ComprobantePago cp, ResultSet rs) throws SQLException {
        cp.setId(rs.getInt("idComprobantePago"));
        cp.setFecha_pago(rs.getDate("fecha_pago"));
        cp.setMetodoPago(MetodoPago.valueOf(rs.getString("metodo_pago")));
        cp.setMonto_total(rs.getDouble("monto_total"));
        cp.setOrden(ordenVentaDAO.obtener(rs.getInt("id_orden")));
       
    }
    
    @Override
    protected ComprobantePago createFromResultSet(ResultSet rs) throws SQLException {
        ComprobantePago comprobante = new ComprobantePago();
        
        comprobante.setId(rs.getInt("idComprobantePago"));
        OrdenVenta orden = new OrdenVenta();
        orden.setId(rs.getInt("id_orden"));
        comprobante.setOrden(orden);
        comprobante.setMetodoPago(MetodoPago.valueOf(rs.getString("metodo_pago")));
        comprobante.setFecha_pago(rs.getTimestamp("fecha_pago"));
        comprobante.setMonto_total(rs.getDouble("monto_total"));
        return comprobante;
    }
    
    @Override
    protected void setId(ComprobantePago entity, Integer id) {
        entity.setId(id);
        
    }
    
}
