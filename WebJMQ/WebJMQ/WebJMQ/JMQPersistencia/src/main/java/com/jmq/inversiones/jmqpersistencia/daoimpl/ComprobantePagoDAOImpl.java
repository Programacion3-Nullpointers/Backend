package com.jmq.inversiones.jmqpersistencia.daoimpl;

import com.jmq.inversiones.jmqpersistencia.BaseDAOImpl;
import com.jmq.inversiones.dominio.pagos.ComprobantePago;
import com.jmq.inversiones.dominio.pagos.MetodoPago;
import com.jmq.inversiones.jmqpersistencia.dao.ComprobantePagoDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public abstract class ComprobantePagoDAOImpl extends BaseDAOImpl<ComprobantePago> implements ComprobantePagoDAO{

    private final OrdenVentaDAOImpl ordenVentaDAO = new OrdenVentaDAOImpl();

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO ComprobantePago(idOrdenVenta, metodo_pago, fecha_pago, monto_total) VALUES (?,?,?,?)";
    }

    @Override
    protected String getUpdateQuery() {
       return "UPDATE ComprobantePago SET idOrdenVenta= ?, metodoPago = ?, fecha_pago = ?, monto_total=? WHERE id=?";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM ComprobantePago WHERE id = ?";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELEC * FROM ComprobantePago WHERE id = ?";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELCT * FROM ComprobantePago";
    }

    @Override
    protected void setInsertParameters(PreparedStatement ps, ComprobantePago entity) throws SQLException {
        ps.setInt(1,entity.getOrden().getId());
        ps.setString(2, entity.getMetodoPago().name()); //usamos el nombre del enum
        ps.setTimestamp(3, new Timestamp(entity.getFecha_pago().getTime()));
        ps.setDouble(4, entity.getMonto_total());
    }

    @Override
    protected void setUpdateParameters(PreparedStatement ps, ComprobantePago entity) throws SQLException {
        ps.setInt(1, entity.getOrden().getId());
        ps.setString(2, entity.getMetodoPago().name());
        ps.setTimestamp(3, new Timestamp(entity.getFecha_pago().getTime()));
        ps.setDouble(4, entity.getMonto_total());
        ps.setInt(5, entity.getId());
    }

    protected void  fillFromResultSetBase(ComprobantePago cp, ResultSet rs) throws SQLException {
        cp.setId(rs.getInt("idComprobantePago"));
        cp.setFecha_pago(rs.getDate("fecha_pago"));
        cp.setMetodoPago(MetodoPago.valueOf(rs.getString("metodo_pago")));
        cp.setMonto_total(rs.getDouble("monto_total"));
        cp.setOrden(ordenVentaDAO.obtener(rs.getInt("idOrdenVenta")));
       
    }
    
    @Override
    protected ComprobantePago createFromResultSet(ResultSet rs) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    @Override
    protected void setId(ComprobantePago entity, Integer id) {
        entity.setId(id);
        
    }
    
}
